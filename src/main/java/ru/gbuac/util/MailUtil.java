package ru.gbuac.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class MailUtil {
    //@Autowired
    public JavaMailSender emailSender;

    public void sendEmailDocOnAgreement(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Документ на согласовании");
        message.setText("На согласование поступил документ");
        this.emailSender.send(message);
    }
}
