package com.inn.appointment.JWT;

import com.inn.appointment.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Autowired
    UserDao userDao;

    private com.inn.appointment.POJO.User UserDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside logUserByName {}", username);
        UserDetail = userDao.findByEmailId(username);
        if (!Objects.isNull(UserDetail)) {
            return new User(UserDetail.getEmail(), UserDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.inn.appointment.POJO.User getUserDetail() {
        return UserDetail;
    }

}
