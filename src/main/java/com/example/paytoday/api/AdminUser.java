package com.example.paytoday.api;


import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.Util.WalletStatus;
import com.example.paytoday.dao.RetailerDAO;
import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.dao.WalletDAO;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.User;
import com.example.paytoday.model.Wallet;
import com.example.paytoday.security.AES;
import com.example.paytoday.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@org.springframework.web.bind.annotation.RestController


@RequestMapping("/adminUser")
public class AdminUser {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RetailerDAO retailerDAO;

    @Autowired
    private WalletDAO walletDAO;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil login(@RequestBody User user) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            user.setPassword(AES.encryptionUtil(user.getPassword()));
            User data = userDAO.getUserforLogin(user);
             if(data!=null){
                responseUtil.setStatusCode("200");
                responseUtil.setMessage("Login successfully");
                responseUtil.setAccesToken(jwtTokenProvider.createToken(user.getEmail(), Arrays.asList("ADMIN")));
             }
            else{
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("Invalid Credentails");
            }
            return responseUtil;
        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("error in getting response");
            return responseUtil;
        }
    }

    @RequestMapping(value ="/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil signUp(@RequestBody User user) {
        ResponseUtil responseUtil = null;
        try{
            responseUtil = validateUserdata(user);
            if(responseUtil.getStatusCode().equals("500"))
                return responseUtil;
            else{

                String password = AES.encryptionUtil(user.getPassword());
                if(!password.isEmpty()&& password != null)
                    user.setPassword(password);
                else {
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                    return responseUtil;
                }

                Long id = userDAO.create(user);

                if(id > 0){
                    responseUtil.setStatusCode("200");
                    responseUtil.setMessage("user data saved");
                }else{
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                }
                return responseUtil;
            }

        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("User Already registered");
            return responseUtil;
        }
    }


    @RequestMapping(value ="/getOnboardedUsers", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Retailer> getOnboardedUsers(){
        List<Retailer> data = new ArrayList<>();
        try{
            data = retailerDAO.getRetailerByStatus(RetailerStatus.ONBOARDED);
            return data;

        }catch (Exception e){
            e.printStackTrace();

            return data;
        }
    }


    @RequestMapping(value ="/getWalletRequest", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, String>> getPendingWalletReq(){
        List<Map<String, String>> response = new ArrayList<>();
        try{
            response = retailerDAO.getWalletRequest(UserType.ADMIN.name());
            return response;

        }catch (Exception e){
            e.printStackTrace();
            return response;
        }
    }


    @RequestMapping(value ="/approveWalletReq", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateUserByEmail(@RequestParam String walletRef){
        ResponseUtil responseUtil = new ResponseUtil();
        try{

            Wallet wallet = walletDAO.getById(new Wallet(), Long.parseLong(walletRef.split("-")[1]));
            wallet.setStatus(WalletStatus.APPROVED.getValue());
            walletDAO.update(wallet);

            Retailer retailer = retailerDAO.getById(new Retailer(), Long.parseLong(wallet.getUserId()));


            if(wallet == null){
                responseUtil.setStatusCode("200");
                responseUtil.setMessage("No Req found!");
                return responseUtil;
            }else{
                if(retailer.getRetailerStatus() == RetailerStatus.ONBOARDED.getValue()) {
                    retailer.setRetailerStatus(RetailerStatus.ACTIVE.getValue());
                    retailer.setAllowRecharge(Boolean.TRUE);
                    retailer.setAllowDMT(Boolean.TRUE);
                    retailer.setAllowBBPS(Boolean.TRUE);
                    retailer.setAllowAEPS(Boolean.TRUE);
                    retailerDAO.update(retailer);
                }

                responseUtil.setStatusCode("200");
                responseUtil.setMessage("Wallet req updated!");
                return responseUtil;
            }
        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage(e.getMessage());
            return responseUtil;
        }
    }







    private static ResponseUtil validateUserdata(User user){

        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatusCode("500");
        if(user.getName()==null || user.getName().isEmpty())
            responseUtil.setMessage("Username is required");
        else if(user.getEmail()==null || user.getEmail().isEmpty())
            responseUtil.setMessage("Email is required");
        else if(user.getPassword()==null || user.getPassword().isEmpty())
            responseUtil.setMessage("Password is required");


        else if(user.getMobile()==null || user.getMobile().isEmpty())
            responseUtil.setMessage("Mobile no is required");
        else{
            responseUtil.setStatusCode("200");
            responseUtil.setMessage("user data is valid");
        }
        return responseUtil;





    }


}

