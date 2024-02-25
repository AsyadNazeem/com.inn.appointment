package com.inn.appointment.rest;


import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/channelling")
public interface ChannellingRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewChannelling(@RequestBody Map<String, String> requestMap);

    @GetMapping("/get")
    ResponseEntity<List<ChannellingWrapper>> getAllChannelling();


}
