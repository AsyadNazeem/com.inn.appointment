package com.inn.appointment.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AppointmentService {

    ResponseEntity<String> addNewAppointment (Map<String, String> requestMap);

}


