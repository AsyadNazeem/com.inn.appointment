package com.inn.appointment.dao;

import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannellingDao extends JpaRepository<Channelling, Integer>{

    List<ChannellingWrapper> getAllChannelling();

}
