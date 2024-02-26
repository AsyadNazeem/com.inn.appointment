package com.inn.appointment.restImpl;

import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.rest.ChannellingRest;
import com.inn.appointment.service.ChannellingServices;
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
public class ChannellingRestImpl implements ChannellingRest {
    /**
     * @param requestMap
     * @return
     */

    @Autowired
    ChannellingServices channellingServices;

    @Override
    public ResponseEntity<String> addNewChannelling(Map<String, String> requestMap) {
        try {
            return channellingServices.addNewChannelling(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @return
     */
    @Override
    public ResponseEntity<List<ChannellingWrapper>> getAllChannelling() {
        try {
            return channellingServices.getAllChannelling();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> updateChannelling(Map<String, String> requestMap) {
        try {
            return channellingServices.updateChannelling(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<String> deleteChannelling(Integer id) {
        try {
            return channellingServices.deleteChannelling(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
