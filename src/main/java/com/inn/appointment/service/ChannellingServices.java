package com.inn.appointment.service;

import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ChannellingServices {
    ResponseEntity<String> addNewChannelling(Map<String, String> requestMap);

    ResponseEntity<List<ChannellingWrapper>> getAllChannelling ();

    ResponseEntity<String> updateChannelling(Map<String, String> requestMap);

    ResponseEntity<String> deleteChannelling(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
}
