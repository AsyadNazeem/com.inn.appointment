package com.inn.appointment.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppointmentUtils {


    private AppointmentUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responsMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\""+responsMessage+"\"}", httpStatus);
    }
}
