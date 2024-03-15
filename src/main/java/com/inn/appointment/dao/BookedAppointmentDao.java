package com.inn.appointment.dao;

import com.inn.appointment.POJO.BookedAppointments;
import com.inn.appointment.wrapper.ChannellingWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookedAppointmentDao extends JpaRepository<BookedAppointments, Integer> {

    List<ChannellingWrapper> getAllBookedAppointment();

    List<ChannellingWrapper> getBookingById(Integer id);
}
