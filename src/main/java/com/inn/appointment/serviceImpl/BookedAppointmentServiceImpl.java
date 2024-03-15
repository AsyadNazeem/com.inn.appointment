package com.inn.appointment.serviceImpl;

import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.POJO.BookedAppointments;
import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.POJO.User;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.BookedAppointmentDao;
import com.inn.appointment.service.BookedAppointmentService;
import com.inn.appointment.utils.AppointmentUtils;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookedAppointmentServiceImpl implements BookedAppointmentService {

    @Autowired
    BookedAppointmentDao bookedAppointmentDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> bookAppointment(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin() || jwtFilter.isUser()){
                if(validateAppointmentMap(requestMap, false)){
                    bookedAppointmentDao.save(getBookedAppointmentFromMap(requestMap, false));
                    return AppointmentUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return AppointmentUtils.getResponseEntity(AppointmentConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAppointmentMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private BookedAppointments getBookedAppointmentFromMap(Map<String, String>  requestMap, boolean isAdd) {
        Channelling channelling = new Channelling();
        channelling.setId(Integer.parseInt(requestMap.get("channellingId")));

        BookedAppointments bookedAppointments = new BookedAppointments();
        if(isAdd){
            bookedAppointments.setId(Integer.parseInt(requestMap.get("id")));
        }else{
            bookedAppointments.setStatus("true");
        }
        bookedAppointments.setChannelling(channelling);
        bookedAppointments.setName(requestMap.get("name"));
        bookedAppointments.setAge(requestMap.get("age"));
        bookedAppointments.setEmail(requestMap.get("email"));
        bookedAppointments.setMobile(requestMap.get("mobile"));
        bookedAppointments.setDoctor(requestMap.get("doctor"));
        return bookedAppointments;
    }

    @Override
    public ResponseEntity<List<ChannellingWrapper>> getAllBookedAppointment() {
        try {
            return new ResponseEntity<>(bookedAppointmentDao.getAllBookedAppointment(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ChannellingWrapper>> getBookingById(Integer id) {
        try {
            return new ResponseEntity<>(bookedAppointmentDao.getBookingById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
