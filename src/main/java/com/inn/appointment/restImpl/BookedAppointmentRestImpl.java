package com.inn.appointment.restImpl;

import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.rest.BookedAppointmentRest;
import com.inn.appointment.service.BookedAppointmentService;
import com.inn.appointment.utils.AppointmentUtils;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class BookedAppointmentRestImpl implements BookedAppointmentRest {

    @Autowired
    BookedAppointmentService  bookedAppointmentService;

    @Override
    public ResponseEntity<String> bookAppointment(Map<String, String> requestMap) {
        try{
            return bookedAppointmentService.bookAppointment(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ChannellingWrapper>> getAllBookedAppointment() {
        try{
            return bookedAppointmentService.getAllBookedAppointment();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ChannellingWrapper>> getBookingById(Integer id) {
        try {
            return bookedAppointmentService.getBookingById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
