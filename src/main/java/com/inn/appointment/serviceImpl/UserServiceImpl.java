package com.inn.appointment.serviceImpl;

import com.google.common.base.Strings;
import com.inn.appointment.JWT.CustomerUserDetailsService;
import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.JWT.JwtUtil;
import com.inn.appointment.POJO.User;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.UserDao;
import com.inn.appointment.service.UserService;
import com.inn.appointment.utils.AppointmentUtils;
import com.inn.appointment.utils.EmailsUtils;
import com.inn.appointment.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailsUtils emailsUtils;

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
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;

    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");

        // Generate a unique number based on a counter (replace this with your logic)
        String uniqueNumber = generateUniqueID();
        user.setUniqueNumber(uniqueNumber);

        return user;
    }

    private String generateUniqueID() {
        // Characters to use for generating the unique ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Length of the unique ID
        int length = 6;

        // StringBuilder to build the unique ID
        StringBuilder uniqueID = new StringBuilder();

        // Generate the unique ID by randomly selecting characters from the set
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            uniqueID.append(characters.charAt(randomIndex));
        }

        return uniqueID.toString();
    }


//    private String generateUniqueID() {
//        // Prefix for user
//        String prefix = "U";
//
//        // Generate a random number (you can customize the length)
//        int randomNumber = generateRandomNumber(1000, 9999);
//
//        // Combine the prefix and random number
//        return prefix + randomNumber;
//    }
//
//    private int generateRandomNumber(int min, int max) {
//        // Generate a random number between the specified range
//        return ThreadLocalRandom.current().nextInt(min, max + 1);
//    }



    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"Wait For Admin Approval\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"Wrong Credential\"}", HttpStatus.BAD_REQUEST);
    }

    /**
     * @return
     */
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);

            }
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
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));

                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmins(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return AppointmentUtils.getResponseEntity("Status Updated", HttpStatus.OK);
                } else {
                    AppointmentUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
                }
            } else {
                return AppointmentUtils.getResponseEntity(AppointmentConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmins(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailsUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:- " + user + " \n is approved by \n ADMIN :-" + jwtFilter.getCurrentUser(), allAdmin);
        } else {
            emailsUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:- " + user + " \n is disabled by \n ADMIN :-" + jwtFilter.getCurrentUser(), allAdmin);
        }
    }

    /**
     * @return
     */
    @Override
    public ResponseEntity<String> checkToken() {
        return AppointmentUtils.getResponseEntity("true", HttpStatus.OK);
    }

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userobj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userobj.equals(null)) {
                if (userobj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userobj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userobj);
                    return AppointmentUtils.getResponseEntity("PASSWORD_CHANGED", HttpStatus.OK);
                }
                return AppointmentUtils.getResponseEntity("INVALID_OLD_PASSWORD", HttpStatus.BAD_REQUEST);
            }
            return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailsUtils.forgotMail(user.getEmail(), "Credentials by Appointment Management System", user.getPassword());
            return AppointmentUtils.getResponseEntity("Password has been sent to your email", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}