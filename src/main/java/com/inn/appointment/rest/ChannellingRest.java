package com.inn.appointment.rest;


import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/channelling")
public interface ChannellingRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewChannelling(@RequestBody Map<String, String> requestMap);

    @GetMapping("/get")
    ResponseEntity<List<ChannellingWrapper>> getAllChannelling();

    @PostMapping("/update")
    ResponseEntity<String> updateChannelling(@RequestBody Map<String, String> requestMap);

    @PostMapping("/delete/{id}")
    ResponseEntity<String> deleteChannelling(@PathVariable Integer id);

    @PostMapping("/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping("/getByChannelling/{id}")
    ResponseEntity<List<ChannellingWrapper>> getByChannelling(@PathVariable Integer id);
}
