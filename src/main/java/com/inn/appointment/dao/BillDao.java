package com.inn.appointment.dao;

import com.inn.appointment.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDao extends JpaRepository<Bill, Integer> {

    List<Bill> getAllBIlls();
    List<Bill> getBillsByUserName(@Param("username") String username);

}
