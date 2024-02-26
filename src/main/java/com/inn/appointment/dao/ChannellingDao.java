package com.inn.appointment.dao;

import com.inn.appointment.POJO.Channelling;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ChannellingDao extends JpaRepository<Channelling, Integer>{

    List<ChannellingWrapper> getAllChannelling();

    @Modifying
    @Transactional
    Integer updateAppointmentStatus(@Param("status") String status, @Param("id") Integer id);

    List<ChannellingWrapper> getByChannelling(@Param("id") Integer id);

    ChannellingWrapper getChannellingById(@Param("id") Integer id);
}
