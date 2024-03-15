package com.inn.appointment.service;

import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BookedAppointmentService {
    ResponseEntity<String> bookAppointment(Map<String, String> requestMap);

    ResponseEntity<List<ChannellingWrapper>> getAllBookedAppointment();

    ResponseEntity<List<ChannellingWrapper>> getBookingById(Integer id);
}
