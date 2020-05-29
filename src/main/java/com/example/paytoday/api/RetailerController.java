package com.example.paytoday.api;

import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserRole;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.dao.RetailerDAO;
import com.example.paytoday.dao.WalletDAO;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.User;
import com.example.paytoday.model.Wallet;
import com.example.paytoday.security.AES;
import com.example.paytoday.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@org.springframework.web.bind.annotation.RestController


@RequestMapping("/retailer")
public class RetailerController {


    @Autowired
    private RetailerDAO retailerDAO;

    @Autowired
    private WalletDAO walletDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @RequestMapping(value ="/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil signUp(@RequestBody Retailer retailer) {
        ResponseUtil responseUtil = null;
        try{
            responseUtil = validateUserdata(retailer);
            if(responseUtil.getStatusCode().equals("500"))
                return responseUtil;
            else{

                String password = AES.encryptionUtil(retailer.getPassword());
                if(!password.isEmpty()&& password != null){
                    retailer.setPassword(password);
                    populateRetailerdata(retailer);
                    if (retailer.getUserType() == 1) {
                        Retailer agentData = retailerDAO.getUserbyEmail(retailer.getAgentEmail());
                        retailer.setAgentId(agentData.getId());
                    } else
                        retailer.setAgentId(0L);
                }

                else {
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                    return responseUtil;
                }

                Long id = retailerDAO.create(retailer);

                if(id > 0){
                    responseUtil.setStatusCode("200");
                    responseUtil.setMessage("user data saved");
                }else{
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                }
                return responseUtil;
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("User Already registered");
            return responseUtil;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil login(@RequestBody Retailer retailer) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            retailer.setPassword(AES.encryptionUtil(retailer.getPassword()));
            Retailer data = retailerDAO.getUserforLogin(retailer);
            if (data != null && data.getState().equals(2)) {
                responseUtil.setStatusCode("200");
                responseUtil.setMessage("Login successfully");
                responseUtil.setAccesToken(jwtTokenProvider.createToken(retailer.getEmail(), Arrays.asList("ADMIN")));
            } else {
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("Process on pending...");
            }
            return responseUtil;
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("error in getting response");
            return responseUtil;
        }
    }

    private static String fileUpload(String email, MultipartFile fileData) throws IOException {
        String loc = "D:\\paytoday\\" + email;
        String filename = new SimpleDateFormat("YYYYMMDDHHmmSS").format(new Date()) + fileData.getOriginalFilename();
        File file = new File("D:\\paytoday\\", email);
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

    @RequestMapping(value = "/addWallet", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil addWallet(@RequestParam String wallet, @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        ResponseUtil responseUtil = new ResponseUtil();
        ObjectMapper jsonData = new ObjectMapper();
        Wallet walletObj = jsonData.readValue(wallet, Wallet.class);
        try {
            responseUtil = validateWallet(walletObj);
            Retailer data = retailerDAO.getUserbyEmail(walletObj.getUser_id());
            if (data != null) {
                String filename = fileUpload(walletObj.getUser_id(), file);
                walletObj.setUser_id(data.getId().toString());
                walletObj.setImgUrl(filename);
                Long id = walletDAO.create(walletObj);
                if (id != null) {
                    responseUtil.setStatusCode("200");
                    responseUtil.setMessage("wallet data saved");
                } else {
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("Error in saving wallet data");
                }

            }
            else{
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("Invalid data");
            }
            return responseUtil;

        }
        catch (Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("error in getting response");
            return responseUtil;

        }
    }




    private static ResponseUtil validateUserdata(Retailer retailer){

        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatusCode("500");
        if(retailer.getName()==null || retailer.getName().isEmpty())
            responseUtil.setMessage("Username is required");
        else if(retailer.getEmail()==null || retailer.getEmail().isEmpty())
            responseUtil.setMessage("Email is required");
        else if(retailer.getPassword()==null || retailer.getPassword().isEmpty())
            responseUtil.setMessage("Password is required");
        else if(retailer.getMobile()==null || retailer.getMobile().isEmpty())
            responseUtil.setMessage("Mobile no is required");
        else{
            responseUtil.setStatusCode("200");
            responseUtil.setMessage("user data is valid");
        }
        return responseUtil;
    }
    private static ResponseUtil validateWallet(Wallet wallet){

        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatusCode("500");
        if(wallet.getAmount().equals(BigDecimal.ZERO) || wallet.getAmount() == null)
            responseUtil.setMessage("Amount is required");
        else if(wallet.getTransactionType().equals(null) )
            responseUtil.setMessage("transfer_type is required");
        else if(wallet.getUser_id() == null || wallet.getUser_id().isEmpty())
            responseUtil.setMessage("User_id is required");
        else if(wallet.getTransactionType()==null || wallet.getTransactionType().isEmpty())
            responseUtil.setMessage("Option is required");
        else{
            responseUtil.setStatusCode("200");
            responseUtil.setMessage("Wallet is valid");
        }
        return responseUtil;
    }


    private void populateRetailerdata(Retailer retailer){
        retailer.setUserType(retailer.getUserType());
        retailer.setUserRole(UserRole.RETAILER.getValue());
       retailer.setRetailerStatus(RetailerStatus.ONBOARDED.getValue());
       retailer.setAllowAEPS(Boolean.FALSE);
       retailer.setAllowBBPS(Boolean.FALSE);
       retailer.setAllowDMT(Boolean.FALSE);
       retailer.setAllowPan(Boolean.FALSE);
       retailer.setAllowRecharge(Boolean.FALSE);
    }


}
