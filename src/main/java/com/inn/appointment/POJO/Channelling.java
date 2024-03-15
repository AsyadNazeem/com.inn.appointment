package com.inn.appointment.POJO;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "Channelling.getAllChannelling", query = "select new com.inn.appointment.wrapper.ChannellingWrapper(c.id, c.doctor, c.specialization, c.hospital, c.appointments.id, c.appointments.name, c.description, c.status, c.date, c.amount, c.appointment_count) from Channelling c")

@NamedQuery(name = "Channelling.updateAppointmentStatus", query = "update Channelling c set c.status = :status where c.id = :id")

@NamedQuery(name = "Channelling.getByChannelling", query = "select new com.inn.appointment.wrapper.ChannellingWrapper(c.id, c.doctor) from Channelling c where c.appointments.id = :id and c.status = 'true'")

@NamedQuery(name = "Channelling.getChannellingById", query = "select new com.inn.appointment.wrapper.ChannellingWrapper(c.id, c.doctor, c.specialization, c.hospital, c.description) from Channelling c where c.id = :id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "channelling")
public class Channelling implements Serializable {

        public static final long serialVersionUID = 123456L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "date")
        private String date;

        @Column(name = "doctor")
        private String doctor;

        @Column(name = "specialization")
        private String specialization;

        @Column(name = "hospital")
        private String hospital;

        @ManyToOne (fetch = FetchType.LAZY)
        @JoinColumn(name = "appointment_fk", nullable = false)
        private Appointments appointments;

        @Column(name = "description")
        private String description;

        @Column(name = "status")
        private String status;

        @Column(name = "amount")
        private String amount;

        @Column(name = "appointment_count")
        private String appointment_count;
}
