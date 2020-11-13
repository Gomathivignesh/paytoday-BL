package com.example.paytoday.api;


import com.example.paytoday.Util.Constants;
import com.example.paytoday.data.BBPSReqData;
import com.example.paytoday.data.BBPSResData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("BBPSHandler")
public class BBPSController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bbps.registration}")
    private String registrationAPI;

    @Value("${bbps.bbpsusername}")
    private String bbpsUsername;

    @Value("${bbps.bbpspassword}")
    private String bbpsPassword;

    @Value("${bbps.billCategories}")
    private String billCategories;

    @Value("${bbps.billerByCategories}")
    private String billerByCategories;

    @Value("${bbps.getAmount}")
    private String billAmount;

    @Value("${bbps.payOffline}")
    private String payOffline;





    @RequestMapping(value = "/getBillerCategory", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity getBillerCategories(){

        BBPSReqData bbpsData = populateData(new BBPSReqData());
        bbpsData.setCoverage("IND");

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(bbpsUsername, bbpsPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(bbpsData, headers);

        Object res = restTemplate.exchange(billCategories, HttpMethod.POST, entity, Object.class).getBody();

        JSONObject json = new JSONObject(new Gson().toJson(res));
        JSONArray jArray= json.getJSONArray("Response");

        BBPSResData bbpsResData = new BBPSResData();
        List<Object> billerCategory = new ArrayList<>();

        if (jArray != null)
            for (int i=0;i<jArray.length();i++){
                HashMap<String, Object> responseData = new Gson().fromJson(jArray.getJSONObject(i).toString(), HashMap.class);
                billerCategory.add(responseData);
            }

        bbpsResData.setMessage(json.getString("ResponseMessage"));
        bbpsResData.setBillerCategory(billerCategory);
        return ResponseEntity.ok(bbpsResData);

    }
    @RequestMapping(value = "/getBillerByCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBillerByCategories(@RequestBody BBPSReqData bbpsReqData){

        populateData(bbpsReqData);


        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(bbpsUsername, bbpsPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(bbpsReqData, headers);

        Object res = restTemplate.exchange(billerByCategories, HttpMethod.POST, entity, Object.class).getBody();

        JSONObject json = new JSONObject(new Gson().toJson(res));
        JSONArray jArray= json.getJSONArray("Response");

        BBPSResData bbpsResData = new BBPSResData();
        List<Object> billerByCategory = new ArrayList<>();

        if (jArray != null)
            for (int i=0;i<jArray.length();i++){
                HashMap<String, Object> responseData = new Gson().fromJson(jArray.getJSONObject(i).toString(), HashMap.class);
                billerByCategory.add(responseData);
            }

        bbpsResData.setMessage(json.getString("ResponseMessage"));
        bbpsResData.setBillerByCategory(billerByCategory);

        System.out.println(new Gson().toJson(bbpsResData));
        return ResponseEntity.ok(bbpsResData);

    }

    @RequestMapping(value = "/getBillerAmount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAmountForBiller(@RequestBody BBPSReqData bbpsReqData){

        populateData(bbpsReqData);
        BBPSResData bbpsResData =null;

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(bbpsUsername, bbpsPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(bbpsReqData, headers);

        Object res = restTemplate.exchange(billAmount, HttpMethod.POST, entity, Object.class).getBody();

        //converted Map to check response is success or failure [if i use json.get() will throw NPE]
        Map<String, String> resMap = (Map)res;

        if(resMap.get("ResponseCode")!=null && resMap.get("ResponseCode").equals("000")){
            System.out.println(new Gson().toJson(res));
            JSONObject responseJson = new JSONObject(new Gson().toJson(res));
            bbpsResData = new BBPSResData();
            bbpsResData.setMessage(responseJson.getString("ResponseMessage"));
            bbpsResData.setStatus(Constants.RESPONSE_SUCCESS_STATUS);
            bbpsResData.setCustomerName(responseJson.getString("CustomerName"));
            bbpsResData.setBillNo(responseJson.getString("BillNumber"));
            bbpsResData.setBillAmount(responseJson.getString("BillAmount"));
            bbpsResData.setTransactionId(responseJson.getString("TransactionId"));
        }
        else{
            //TODO negative flow needs to be done.
        }

        return ResponseEntity.ok(bbpsResData);

    }


    @RequestMapping(value = "/offlinePayment", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  payOffline(@RequestBody BBPSReqData bbpsReqData){
        populateData(bbpsReqData);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(bbpsUsername, bbpsPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(bbpsReqData, headers);

        Object res = restTemplate.exchange(payOffline, HttpMethod.POST, entity, Object.class).getBody();

        //converted Map to check response is success or failure [if i use json.get() will throw NPE]
        Map<String, String> resMap = (Map)res;
        System.out.println(new Gson().toJson(resMap));
        return ResponseEntity.ok(resMap);

    }


    private BBPSReqData populateData(BBPSReqData bbpsReqData){

        bbpsReqData.setSecurityKey("e0f90aae19f8b5d91873eba4eaaf7826");
        bbpsReqData.setRequestBy("MM000600");

        return bbpsReqData;
    }

}
