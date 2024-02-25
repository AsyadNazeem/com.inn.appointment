package com.inn.appointment.serviceImpl;

import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.AppointmentDao;
import com.inn.appointment.service.AppointmentService;
import com.inn.appointment.utils.AppointmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inn.appointment.POJO.Appointments;


import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateAppointmentMap(requestMap, false)) {
                    appointmentDao.save(getAppointmentFromMap(requestMap, false));
                    return AppointmentUtils.getResponseEntity("APPOINTMENT_ADDED", HttpStatus.OK);
                }
            }else{
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS , HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAppointmentMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Appointments getAppointmentFromMap(Map<String, String> requestMap, Boolean isAdd) {
        Appointments appointments = new Appointments();
        if (isAdd) {
            appointments.setId(Integer.parseInt(requestMap.get("id")));
        }
        appointments.setName(requestMap.get("name"));
        return appointments;
    }
}
