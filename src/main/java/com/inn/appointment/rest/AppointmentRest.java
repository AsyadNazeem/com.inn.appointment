package com.inn.appointment.rest;


import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/appointment")
public interface AppointmentRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewAppointment(@RequestBody(required = true)Map<String, String> requestMap);

}
