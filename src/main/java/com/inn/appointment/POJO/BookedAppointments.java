package com.inn.appointment.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "BookedAppointments.getAllBookedAppointment", query = "select new com.inn.appointment.wrapper.ChannellingWrapper(b.id, b.age, b.email, b.mobile, b.name, b.channelling.id, b.status, b.doctor, b.date) from BookedAppointments b")

@NamedQuery(name = "BookedAppointments.getBookingById", query = "select new com.inn.appointment.wrapper.ChannellingWrapper(b.id, b.age, b.email, b.mobile, b.name, b.channelling.id, b.status, b.doctor, b.date) from BookedAppointments b where b.id = :id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "BookedAppointment")

public class BookedAppointments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "channelling_fk", nullable = false)
    private Channelling channelling;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "date")
    private String date;

}
