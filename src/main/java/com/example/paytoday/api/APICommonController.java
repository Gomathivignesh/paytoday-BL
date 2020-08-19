package com.example.paytoday.api;


import com.example.paytoday.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/sharedUtils")
public class APICommonController {

    @Autowired
    RestTemplate restTemplate;


    @RequestMapping(value = "/getState" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStateList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange("http://uat.dhansewa.com/Common/GetState", HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/getDistrictByState" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDistrictsbyState(@RequestBody Object data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <Object> entity = new HttpEntity<Object>(data, headers);

        return restTemplate.exchange("http://uat.mahagram.in/Common/GetDistrictByState", HttpMethod.POST, entity, String.class).getBody();
    }

}
