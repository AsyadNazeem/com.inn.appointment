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
    String age;
    String mobile;
    String email;
    String name;
    Integer channellingId;
    String date;
    String amount;
    String appointment_count;


    public ChannellingWrapper(Integer id, String doctor, String specialization, String hospital, Integer appointmentId, String appointmentName, String description, String status, String date, String amount, String appointment_count) {
        this.id = id;
        this.doctor = doctor;
        this.specialization = specialization;
        this.hospital = hospital;
        this.appointmentsId = appointmentId;
        this.appointmentsName = appointmentName;
        this.description = description;
        this.status = status;
        this.date = date;
        this.amount = amount;
        this.appointment_count = appointment_count;
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

    public ChannellingWrapper(Integer id, String age, String email, String mobile, String name, Integer channellingId, String status, String doctor, String date){
        this.id = id;
        this.age = age;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.channellingId = channellingId;
        this.status = status;
        this.doctor = doctor;
        this.date = date;

    }



    public ChannellingWrapper() {

    }
}
