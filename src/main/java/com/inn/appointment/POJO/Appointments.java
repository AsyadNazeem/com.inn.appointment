package com.inn.appointment.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;



@NamedQuery(name = "Appointments.getAllAppointments", query = "select a from Appointments a")



@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "appointments")


public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}