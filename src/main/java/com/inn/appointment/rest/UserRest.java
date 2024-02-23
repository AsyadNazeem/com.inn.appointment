package com.inn.appointment.rest;


import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @RequestMapping(path = "/signup")
    public ResponseEntity<String> SignUp(@RequestBody (required = true) Map<String,String> requestMap);
}
