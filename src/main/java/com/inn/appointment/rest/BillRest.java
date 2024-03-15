package com.inn.appointment.rest;


import com.inn.appointment.POJO.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/bill")
public interface BillRest  {

    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport (@RequestBody Map<String, Object> requestMap);

    @GetMapping(path = "/getBills")
    ResponseEntity<List<Bill>> getBills();
    
    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@RequestBody Integer id);

}

