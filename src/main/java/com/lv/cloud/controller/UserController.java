package com.lv.cloud.controller;


import com.lv.cloud.dto.User;
import com.lv.cloud.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LvNing
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/api/v1/ribbon")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;


    @ApiOperation(value = "查询所有用户信息", response = User.class)
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> queryAll() {
        return userService.queryUser();

    }


    private <T, A> T exchange(String url, HttpMethod method, Class<T> responseBodyType, A requestBody) {
        RestTemplate template = new RestTemplate();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        // 发送请求
        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = template.exchange(url, method, entity, responseBodyType);
        return resultEntity.getBody();
    }


}
