package com.inn.appointment.service;

import com.inn.appointment.POJO.Appointments;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    ResponseEntity<String> addNewAppointment (Map<String, String> requestMap);

    ResponseEntity< List<Appointments>> getAllAppointment (String filterValue);

    ResponseEntity<String> updateAppointment (Map<String, String> requestMap);

}


