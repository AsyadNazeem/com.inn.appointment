package com.inn.appointment.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Service
public class EmailsUtils {

    @Autowired
    @Qualifier("mailSender")
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        // Provide a valid "from" address or remove this line if not needed
        message.setFrom("tutorialmaster200102@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if (list != null && list.size() > 0) {
            message.setCc(getCcArray(list));
            emailSender.send(message);
        }
    }

    private String[] getCcArray(List<String> list) {
        String[] cc = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cc[i] = list.get(i);
        }
        return cc;
    }
}
