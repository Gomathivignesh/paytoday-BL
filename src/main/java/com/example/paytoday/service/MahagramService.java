package com.example.paytoday.service;

import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.dao.AepsDAO;
import com.example.paytoday.data.AEPSData;
import com.example.paytoday.data.BBPSReqData;
import com.example.paytoday.data.BBPSResData;
import com.example.paytoday.model.AepsInfo;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class MahagramService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AepsDAO aepsDAO;

    @Value("${aeps.registration}")
    private String aepsRegistrationAPI;

    @Value("${bbps.registration}")
    private String bbpsRegistrationAPI;

    @Value("${bbps.bbpsusername}")
    private String bbpsUsername;

    @Value("${bbps.bbpspassword}")
    private String bbpsPassword;


    public ResponseEntity registerAepsUser(AEPSData data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

            Object res = restTemplate.exchange(aepsRegistrationAPI, HttpMethod.POST, entity, Object.class).getBody();
            JSONArray resData = new JSONArray(new Gson().toJson(res));
            System.out.println(new Gson().toJson(resData.getJSONObject(0)));
            if (!resData.getJSONObject(0).isNull("StatusCode") && resData.getJSONObject(0).get("StatusCode").equals("999")  ) {
                return ResponseEntity.ok(new ResponseUtil(200, resData.getJSONObject(0).get("Message").toString()));
            } else if(!resData.getJSONObject(0).isNull("Statuscode") && resData.getJSONObject(0).get("Statuscode").equals("000")) {
                AepsInfo aepsInfo = new AepsInfo();
                aepsInfo.setAepsId(resData.getJSONObject(0).get("bc_id").toString());
                aepsInfo.setStatus("Pending");
                aepsDAO.create(aepsInfo);
                return ResponseEntity.ok(new ResponseUtil(200, "user Registerd"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }


    public  ResponseEntity registerBBPSUser(BBPSReqData bbpsData){
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(bbpsUsername, bbpsPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(bbpsData, headers);

        Object res = restTemplate.exchange(bbpsRegistrationAPI, HttpMethod.POST, entity, Object.class).getBody();

        JSONObject json = new JSONObject(new Gson().toJson(res));
        BBPSResData response = new BBPSResData();

        if(json.isNull("ResponseMessage")) {
            JSONObject responseMessage = json.getJSONArray("Message").getJSONObject(0);
            JSONObject data = json.getJSONArray("Data").getJSONObject(0);
            HashMap<String, Object> responseMsg = new Gson().fromJson(responseMessage.toString(), HashMap.class);
            HashMap<String, Object> responseData = new Gson().fromJson(data.toString(), HashMap.class);

            response.setMessage(String.valueOf(responseMsg.get("message")));
            response.setAgentId(String.valueOf(responseData.get("agentid")));
            response.setAgentId(String.valueOf(responseData.get("state")));
            return ResponseEntity.ok(response);
        }
        else{
            response.setMessage(String.valueOf(json.get("ResponseMessage")));
            return ResponseEntity.ok(response);
        }
    }
}
