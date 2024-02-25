package com.inn.appointment.serviceImpl;

import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.POJO.Appointments;
import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.AppointmentDao;
import com.inn.appointment.dao.ChannellingDao;
import com.inn.appointment.service.ChannellingServices;
import com.inn.appointment.utils.AppointmentUtils;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChannellingServicesImpl implements ChannellingServices {
    @Autowired
    ChannellingDao channellingDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewChannelling(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateAppointmentMap(requestMap, false)) {
                    channellingDao.save(getProductFromMap(requestMap, false));
                    return AppointmentUtils.getResponseEntity("Product Added SuccessFully", HttpStatus.OK);
                }
                return AppointmentUtils.getResponseEntity(AppointmentConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateAppointmentMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("doctor")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Channelling getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Appointments appointments = new Appointments();
        appointments.setId(Integer.parseInt(requestMap.get("appointmentId")));

        Channelling channelling = new Channelling();
        if (isAdd) {
            channelling.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            channelling.setStatus("true");
        }
        channelling.setAppointments(appointments);
        channelling.setDoctor(requestMap.get("doctor"));
        channelling.setSpecialization(requestMap.get("specialization"));
        channelling.setHospital(requestMap.get("hospital"));
        channelling.setDescription(requestMap.get("description"));
        return channelling;
    }

    /**
     * @return
     */
    @Override
    public ResponseEntity<List<ChannellingWrapper>> getAllChannelling() {
        try {
            return new ResponseEntity<>(channellingDao.getAllChannelling(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
