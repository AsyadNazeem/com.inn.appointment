package com.inn.appointment.wrapper;

import lombok.Data;

@Data
public class ChannellingWrapper {

    Integer id;
    String doctor;
    String specialization;
    String hospital;
    String description;
    String status;
    Integer appointmentsId;
    String appointmentsName;


    public ChannellingWrapper(Integer id, String doctor, String specialization, String hospital, Integer appointmentId, String appointmentName, String description, String status) {
        this.id = id;
        this.doctor = doctor;
        this.specialization = specialization;
        this.hospital = hospital;
        this.appointmentsId = appointmentId;
        this.appointmentsName = appointmentName;
        this.description = description;
        this.status = status;
    }


    public ChannellingWrapper(Integer id, String doctor) {
        this.id = id;
        this.doctor = doctor;
    }

    public ChannellingWrapper(Integer id, String doctor, String specialization, String hospital, String description) {
        this.id = id;
        this.doctor = doctor;
        this.specialization = specialization;
        this.hospital = hospital;
        this.description = description;
    }

    public ChannellingWrapper() {

    }
}
