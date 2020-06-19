package com.example.paytoday.api;


import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.Util.RetailerStatus;
import com.example.paytoday.Util.UserType;
import com.example.paytoday.dao.RetailerDAO;
import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.model.Retailer;
import com.example.paytoday.model.User;
import com.example.paytoday.security.AES;
import com.example.paytoday.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public Map<String, String> getPendingWalletReq(){
        Map<String, String> response = new HashMap<>();
        try{
            response = retailerDAO.getWalletRequest(UserType.ADMIN.name());
            return response;

        }catch (Exception e){
            e.printStackTrace();
            return response;
        }
    }




    @RequestMapping(value ="/updateUser", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil updateUserByEmail(@RequestBody Retailer data){
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            Retailer retailer = retailerDAO.getUserbyEmail(data.getEmail());
            if(retailer == null){
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("user data is not found!");
                return responseUtil;
            }else{
                retailer.setRetailerStatus(RetailerStatus.ACTIVE.getValue());
                retailer.setAllowRecharge(Boolean.TRUE);
                retailer.setAllowDMT(Boolean.TRUE);
                retailer.setAllowBBPS(Boolean.TRUE);
                retailer.setAllowAEPS(Boolean.TRUE);
                retailerDAO.update(retailer);

                responseUtil.setStatusCode("200");
                responseUtil.setMessage("User data Updated!");
                return responseUtil;
            }
        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("User Already registered");
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

