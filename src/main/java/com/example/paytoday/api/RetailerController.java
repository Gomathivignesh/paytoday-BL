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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Arrays;

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

        }catch(Exception e){
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
        try{
            retailer.setPassword(AES.encryptionUtil(retailer.getPassword()));
            Retailer data = retailerDAO.getUserforLogin(retailer);
            if(data!=null){
                responseUtil.setStatusCode("200");
                responseUtil.setMessage("Login successfully");
                responseUtil.setAccesToken(jwtTokenProvider.createToken(retailer.getEmail(), Arrays.asList("ADMIN")));
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

    @RequestMapping(value = "/addWallet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil addWallet(@RequestBody Wallet wallet) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            responseUtil = validateWallet(wallet);
            Retailer data = retailerDAO.getUserbyEmail(wallet.getUser_id());
            if(data!=null){
               wallet.setUser_id(data.getId().toString());
               Long id = walletDAO.create(wallet);
               if(id != null){
                   responseUtil.setStatusCode("200");
                   responseUtil.setMessage("wallet data saved");
               }
               else{
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
       retailer.setUserType(UserType.RETAILER.getValue());
       retailer.setUserRole(UserRole.RETAILER.getValue());
       retailer.setRetailerStatus(RetailerStatus.ONBOARDED.getValue());
       retailer.setAllowAEPS(Boolean.FALSE);
       retailer.setAllowBBPS(Boolean.FALSE);
       retailer.setAllowDMT(Boolean.FALSE);
       retailer.setAllowPan(Boolean.FALSE);
       retailer.setAllowRecharge(Boolean.FALSE);
    }


}
