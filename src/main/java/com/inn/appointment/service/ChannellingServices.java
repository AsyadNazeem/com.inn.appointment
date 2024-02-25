package com.inn.appointment.service;

import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ChannellingServices {
    ResponseEntity<String> addNewChannelling(Map<String, String> requestMap);

    ResponseEntity<List<ChannellingWrapper>> getAllChannelling ();

}