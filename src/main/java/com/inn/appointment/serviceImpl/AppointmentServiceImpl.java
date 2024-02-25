package com.inn.appointment.serviceImpl;

import com.google.common.base.Strings;
import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.AppointmentDao;
import com.inn.appointment.service.AppointmentService;
import com.inn.appointment.utils.AppointmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inn.appointment.POJO.Appointments;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateAppointmentMap(requestMap, false)) {
                    appointmentDao.save(getAppointmentFromMap(requestMap, false));
                    return AppointmentUtils.getResponseEntity("APPOINTMENT_ADDED", HttpStatus.OK);
                }
            } else {
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAppointmentMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
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

    /**
     * @param filterValue
     * @return
     */
    @Override
    public ResponseEntity<List<Appointments>> getAllAppointment(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Getting all appointments");
                return new ResponseEntity<List<Appointments>>(appointmentDao.getAllAppointments(), HttpStatus.OK);
            }
            return new ResponseEntity<>(appointmentDao.findAll(), HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<List<Appointments>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> updateAppointment(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateAppointmentMap(requestMap, true)) {
                    Optional optional = appointmentDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                       appointmentDao.save(getAppointmentFromMap(requestMap, true));
                       return AppointmentUtils.getResponseEntity("APPOINTMENT_UPDATED", HttpStatus.OK);
                    }else{
                        return AppointmentUtils.getResponseEntity("APPOINTMENT_NOT_FOUND", HttpStatus.NOT_FOUND);
                    }
                }
            } else {
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
