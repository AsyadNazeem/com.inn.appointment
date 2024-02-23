package com.inn.appointment.serviceImpl;

import com.inn.appointment.POJO.User;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.UserDao;
import com.inn.appointment.service.UserService;
import com.inn.appointment.utils.AppointmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            log.info("Inside signUp {}", requestMap);
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return AppointmentUtils.getResponseEntity("SuccessFully Registered", HttpStatus.OK);
                } else {
                    return AppointmentUtils.getResponseEntity("USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
                }
            } else {
                return AppointmentUtils.getResponseEntity(AppointmentConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
       if(requestMap.containsKey("name") &&  requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
       }
         return false;

    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

}