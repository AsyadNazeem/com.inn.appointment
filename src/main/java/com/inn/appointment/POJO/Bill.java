package com.inn.appointment.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "Bill.getAllBIlls", query = "SELECT b FROM Bill b order by b.id desc")
@NamedQuery(name = "Bill.getBillsByUserName", query = "SELECT b FROM Bill b where b.billedBy = :username order by b.id desc")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Bill")

public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "paymentmethod")
    private String paymentmethod;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "Description", columnDefinition = "JSON")
    private String Description;

    @Column(name = "billedby")
    private String billedBy;


}
