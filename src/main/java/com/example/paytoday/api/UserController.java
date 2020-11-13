package com.example.paytoday.api;


import com.example.paytoday.Util.Constants;
import com.example.paytoday.Util.CustomException;
import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.WalletStatus;
import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.dao.WalletDAO;
import com.example.paytoday.data.UserData;
import com.example.paytoday.model.User;
import com.example.paytoday.model.Wallet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Config;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/User")
public class UserController {

    private String SERVER_URL;
    private String realm;
    private String clientId;
    private RealmResource realmResource;

    @Autowired
    private UserDAO userDAO;


    @Autowired
    private WalletDAO walletDAO;

    @Autowired
    public UserController(@Value("${keycloak.auth-server-url}")  String SERVER_URL, @Value("${keycloak.realm}") String realm){
        Keycloak keycloak = Keycloak.getInstance(
                SERVER_URL,
                realm,
               "admin",
                "admin",
                "admin-cli");
        realmResource =  keycloak.realm(realm);
        this.SERVER_URL = SERVER_URL;
        this.realm = realm;
    }



    @ResponseBody
    @RequestMapping(value = "/onBoard", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User user) {
        try {

            UsersResource usersResource = realmResource.users();


            if(realmResource.users().search(user.getUsername()).size() > 0
                    || realmResource.users().count("","",user.getEmail(),"") > 0) {
                return ResponseEntity.ok("Username or Email is already exist");
            }
            else {
                CredentialRepresentation credential = new CredentialRepresentation();
                credential.setType(CredentialRepresentation.PASSWORD);
                credential.setValue(user.getPassword());
                credential.setTemporary(false);
                Map<String, List<String>> attributes = new HashMap<>();


                UserRepresentation userRep = new UserRepresentation();
                userRep.setUsername(user.getUsername());
                userRep.setFirstName(user.getFirstName());
                userRep.setLastName(user.getLastName());
                userRep.setEmail(user.getEmail());
                userRep.setRequiredActions(Arrays.asList("VERIFY_EMAIL"));
                userRep.setCredentials(asList(credential));
                userRep.setEnabled(true);

                Response response = usersResource.create(userRep);
                String keycloakId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                populateRetailerdata(user,keycloakId);
                UserResource userResource = usersResource.get(keycloakId);


                if(user.getRole()!=null) {
                    //Assign Role to user
                    ClientRepresentation app1Client = realmResource.clients().findByClientId("Paytoday-Client").get(0);
                    RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId())
                            .roles().get(user.getRole()).toRepresentation();

                    if (userClientRole == null)
                        throw new CustomException("User role not exist");
                    else
                        userResource.roles() //
                                .clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

                    userDAO.create(user);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
        return ResponseEntity.ok("User Created Successfully | Please verify your email to login");

    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) {
        try{
            UsersResource keycloakUser = realmResource.users();
            List<UserRepresentation> userResource;
            if(EmailValidator.getInstance().isValid(user.getUsername()))
                userResource =  realmResource.users().search("","","",user.getUsername(),null,null);
            else
                userResource =  realmResource.users().search(user.getUsername());

            if(userResource.stream().findFirst().isPresent()) {
                UserRepresentation userRep = userResource.stream().findFirst().get();
// Verify mail
//                if(user.getRequiredActions().size()> 0 && user.getRequiredActions().contains("VERIFY_EMAIL")){
//                    realmResource.users().get(user.getId()).executeActionsEmail("kiask","https://www.google.com/",Arrays.asList("VERIFY_EMAIL"));
//                    return ResponseEntity.ok(formatErrorObject("verify email sent to your registered email Id"));
//                }

                TokenManager token = new TokenManager(new Config(SERVER_URL, realm, user.getUsername(), user.getPassword(), "Paytoday-Client", "f718c7ac-4b0e-48e3-b9a4-40d5be86b93a"),
                        new ResteasyClientBuilder().connectionPoolSize(10).build());


                String role = keycloakUser.get(userRep.getId()).roles().getAll()
                        .getClientMappings().get("Paytoday-Client").getMappings().stream().findFirst().get().getName();


                UserData userData = new UserData();
                userData.setUsername(user.getUsername());
                userData.setAccessToken(token.getAccessTokenString());
                userData.setRefreshToken(token.getAccessToken().getRefreshToken());
                userData.setRole(role);

                return ResponseEntity.ok(userData);

            }else
                return ResponseEntity.ok("Invalid Details");


        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }



    @RequestMapping(value = "/addWallet", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO whitelisted for retailer and need to add file later @RequestParam("file") MultipartFile file
    public ResponseEntity addWallet(@RequestParam String wallet) throws JsonProcessingException {
        System.out.println("wallet req: "+ wallet);

        ObjectMapper jsonData = new ObjectMapper();
        Wallet walletObj = jsonData.readValue(wallet, Wallet.class);
        try {
            ResponseEntity responseUtil = validateWallet(walletObj);

            User data = userDAO.getUserbyEmail(walletObj.getUser());
            if (data != null) {
                //TODO needs to finalize the file upload.
                //String filename = fileUpload(walletObj.getUserId(), file);
                walletObj.setUserId(data.getId());
                walletObj.setImgUrl("Dummy.png");
                walletObj.setStatus(WalletStatus.INITIATED.getValue());
                walletObj.setReference("PAY".concat(data.getEmail().substring(0,4))
                        .concat(new SimpleDateFormat("MMDDHHmm").format(new Date())));
                walletObj.setTransactionType(Constants.AMT_CREDIT);
                walletObj.setCreatedDate(new Date());
                walletObj.setCreatedBy(walletObj.getUser().toString());
                Long id = walletDAO.create(walletObj);
                if (id != null) {
                    return ResponseEntity.ok("wallet data saved");
                } else {
                    return ResponseEntity.ok("Error in saving wallet data");
                }
            }
            else{
                return ResponseEntity.ok("Invalid data");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("error in getting response");


        }
    }

    @RequestMapping(value ="/getWalletRequest", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO enable for distributor
    public List<Wallet> getPendingWalletReq(@RequestParam String approverEmail){
        try{
            return walletDAO.getWalletAmount(
                    userDAO.getAllUserByParentEmailId(approverEmail).stream().map(data -> data.getId()).collect(Collectors.toList()));

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value ="/approveWallet", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    //TODO enable for distributor
    public ResponseEntity approveWallet(@RequestParam String walletRef, @RequestParam Boolean doApprove){

        try{
            Wallet wallet = walletDAO.getWalletRequestByRef(walletRef);

            wallet.setStatus(doApprove ? WalletStatus.APPROVED.getValue(): WalletStatus.REJECTED.getValue());
            walletDAO.update(wallet);

            User user = userDAO.getById(new User(), wallet.getUserId());

            if(wallet == null)
               return  ResponseEntity.ok("No Req found!");
            else{
                if(user.getRetailerStatus() == RetailerStatus.ONBOARDED.getValue() && doApprove) {
                    user.setRetailerStatus(RetailerStatus.ACTIVE.getValue());
                    user.setAllowRecharge(Boolean.TRUE);
                    user.setAllowDMT(Boolean.TRUE);
                    user.setAllowBBPS(Boolean.TRUE);
                    user.setAllowAEPS(Boolean.TRUE);
                    userDAO.update(user);
                //TODO Register with mahagram


                }

                return  ResponseEntity.ok("Wallet req updated!");

            }
        }catch(Exception e){
            e.printStackTrace();
            return  ResponseEntity.ok(e.getMessage());

        }
    }

    private void populateRetailerdata(User user, String keycloakId){
        user.setRetailerStatus(RetailerStatus.ONBOARDED.getValue());
        user.setKeycloak_id(keycloakId);
        user.setAllowAEPS(Boolean.FALSE);
        user.setAllowBBPS(Boolean.FALSE);
        user.setAllowDMT(Boolean.FALSE);
        user.setAllowPan(Boolean.FALSE);
        user.setAllowRecharge(Boolean.FALSE);
        if(user.getRole().equals("Paytoday-Admin"))
            user.setParentId(0L);
        else{
            user.setParentId(userDAO.getUserbyEmail(user.getParentEmail()).getId());
        }
    }

    private static ResponseEntity validateWallet(Wallet wallet){


        if(wallet.getAmount().equals(BigDecimal.ZERO) || wallet.getAmount() == null)
            return ResponseEntity.ok("Amount is required");
        else if(wallet.getUser() == null)
            return ResponseEntity.ok("User_id is required");
        else if(wallet.getTransactionType()==null)
            return ResponseEntity.ok("Option is required");
        else
            return ResponseEntity.ok("Wallet is valid");

    }

    private static String fileUpload(String email, MultipartFile fileData) throws IOException {
       String loc = "D:\\paytoday\\" + email + "\\";
       String filename = new SimpleDateFormat("YYYYMMDDHHmmSS").format(new Date()) + fileData.getOriginalFilename();
        File file = new File(loc);

        if (!file.exists()) {
            if (file.mkdir()) {
                fileData.transferTo(new File(file.getAbsolutePath(), filename));
                return loc + filename;
            } else {
                System.out.println("Failed to create directory!");
            }
        } else {
            fileData.transferTo(new File(file.getAbsolutePath(),
                    filename));
            return loc + filename;
        }
        return null;
    }





}
