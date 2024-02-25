package com.inn.appointment.rest;


import com.inn.appointment.POJO.Appointments;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/appointment")
public interface AppointmentRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewAppointment(@RequestBody(required = true)Map<String, String> requestMap);

    @GetMapping("/get")
    ResponseEntity<List<Appointments>> getAllAppointment(@RequestParam(required = false) String filterValue);

    @PostMapping("/update")
    ResponseEntity<String> updateAppointment(@RequestBody(required = true) Map<String, String> requestMap);

}
