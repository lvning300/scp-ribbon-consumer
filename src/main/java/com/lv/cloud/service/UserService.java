package com.lv.cloud.service;

import com.lv.cloud.dto.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "failMessage")
    public List<User> queryUser(){

        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };
        Map<String, Object> params = new HashMap<>();
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange("http://SCP-API-MANAGER/api/v1/user/all", HttpMethod.GET, new HttpEntity<>(params), typeRef);
       return responseEntity.getBody();

    }

    public List<User> failMessage(){
        User build = User.builder()
                .userName("默认值")
                .email("默认值")
                .build();
       return Arrays.asList(build);
    }


}
