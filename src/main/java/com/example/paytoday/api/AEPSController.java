package com.example.paytoday.api;


import com.example.paytoday.Util.ResponseUtil;
import com.example.paytoday.dao.AepsDAO;
import com.example.paytoday.data.AEPSData;
import com.example.paytoday.model.AepsInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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



    @RequestMapping(value = "/registerAEPS" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil registerAepsuser(@RequestBody AEPSData data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

        Object res = restTemplate.exchange(registrationAPI, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println(new Gson().toJson(res));
        //TODO
        if(true){
            AepsInfo aepsInfo = new AepsInfo();
            aepsInfo.setAepsId("BC568360909");
            aepsInfo.setStatus("Pending");
            aepsDAO.create(aepsInfo);
        }

        return new ResponseUtil();

    }

    @RequestMapping(value = "/validateAEPS" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil validateAeps(@RequestBody AEPSData data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

        Object res = restTemplate.exchange(validateAPI, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println(new Gson().toJson(res));

        return new ResponseUtil();

    }


    @RequestMapping(value = "/initiateAeps" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil initiateAeps(@RequestBody AEPSData data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(data, headers);

        Object res = restTemplate.exchange(initiateAPI, HttpMethod.POST, entity, Object.class).getBody();
        System.out.println(new Gson().toJson(res));

        return new ResponseUtil();

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
