package com.inn.appointment.restImpl;

import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.rest.AppointmentRest;
import com.inn.appointment.service.AppointmentService;
import com.inn.appointment.utils.AppointmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppointmentRestImpl implements AppointmentRest {

    @Autowired
    AppointmentService appointmentService;

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap) {
        try {
            return appointmentService.addNewAppointment(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
