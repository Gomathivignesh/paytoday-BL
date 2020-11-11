package com.example.paytoday.api;


import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.dao.AepsDAO;
import com.example.paytoday.data.AEPSData;
import com.example.paytoday.model.AepsInfo;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@RestController
@RequestMapping("/aepsActions")
public class AEPSController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AepsDAO aepsDAO;

    @Value("${aeps.registration}")
    private String registrationAPI;

    @Value("${aeps.validate}")
    private String validateAPI;

    @Value("${aeps.initiate}")
    private String initiateAPI;

    @Value("${aeps.checkTransfer}")
    private String checkTransferAPI;

    @Value("${aeps.payment.url}")
    private String aepsPaymentUrl;





    @RequestMapping(value = "/registerAEPS" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerAepsuser(@RequestBody AEPSData data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

            Object res = restTemplate.exchange(registrationAPI, HttpMethod.POST, entity, Object.class).getBody();
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

    @RequestMapping(value = "/validateAEPS" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateAeps(@RequestBody AEPSData data) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

            Object res = restTemplate.exchange(validateAPI, HttpMethod.POST, entity, Object.class).getBody();
            JSONArray resData = new JSONArray(new Gson().toJson(res));

            if (resData.length() > 0)
                return ResponseEntity.ok(new ResponseUtil(200, "User status " +
                        resData.getJSONObject(0).get("status")));
            else
                return ResponseEntity.ok(new ResponseUtil(200, "User Not exist"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @RequestMapping(value = "/initiateAeps" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity initiateAeps(@RequestBody AEPSData data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

            Object res = restTemplate.exchange(initiateAPI, HttpMethod.POST, entity, Object.class).getBody();
            JSONArray resData = new JSONArray(new Gson().toJson(res));

            if (resData.length() > 0) {
                String paymentUrl = aepsPaymentUrl + resData.getJSONObject(0).get("Result").toString();
                return ResponseEntity.ok(new ResponseUtil(200,
                        resData.getJSONObject(0).get("Message").toString(), paymentUrl));
            } else
                return ResponseEntity.ok(new ResponseUtil(200, "Invalid details"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @RequestMapping(value = "/checkStatus" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil checkStatus(@RequestBody AEPSData data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

        Object res = restTemplate.exchange(checkTransferAPI, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println(new Gson().toJson(res));

        return new ResponseUtil();

    }



}
