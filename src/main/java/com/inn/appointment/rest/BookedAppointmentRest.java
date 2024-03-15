package com.inn.appointment.rest;


import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/bookedAppointment")
public interface BookedAppointmentRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> bookAppointment(@RequestBody Map<String, String> requestMap);

    @GetMapping("/get")
    ResponseEntity<List<ChannellingWrapper>> getAllBookedAppointment();


    @GetMapping("/ /{id}")
    ResponseEntity<List<ChannellingWrapper>> getBookingById(@PathVariable Integer id);

//    @PostMapping("/update")
//    ResponseEntity<String> updateBookedAppointment(@RequestBody Map<String, String> requestMap);
}
