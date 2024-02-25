package com.inn.appointment.dao;

import com.inn.appointment.POJO.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentDao extends JpaRepository<Appointments, Integer> {

    List<Appointments> getAllAppointments();

}
