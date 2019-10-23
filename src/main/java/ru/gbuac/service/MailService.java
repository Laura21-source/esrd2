package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;

    private static List<String> SPRING_PROFILES_ACTIVE;

    @Autowired
    public MailService(@Value("${spring.profiles.active}") String[] actProfiles) {
        SPRING_PROFILES_ACTIVE = Arrays.asList(actProfiles);
    }

    @Value("${email.uri}")
    private String uri;

    public void sendAgreementEmail(String email, int docId, String projectRegNum) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "На согласование/подпись в АИС 'Тариф' поступил документ №" +
                    "<a href='" + uri + "/agree-document?id=" + docId +
                    "'>" + projectRegNum + "</a>";

            message.setContent(htmlMsg, "text/html; charset=UTF-8");

            if (SPRING_PROFILES_ACTIVE.contains("dev")) {
                helper.setTo("MakhrovSS1@develop.mos.ru");
            } else {
                helper.setTo(email);
            }

            helper.setSubject("Система документооборота АИС 'Тариф'");

            this.emailSender.send(message);
        }
        catch (Exception e) {

        }
    }

    public void sendDistributionEmail(String email, int docId, String regNum) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "На распределение в АИС 'Тариф' поступил документ №" +
                    "<a href='" + uri + "/agree-document?id=" + docId +
                    "'>" + regNum + "</a>";

            message.setContent(htmlMsg, "text/html; charset=UTF-8");

            if (SPRING_PROFILES_ACTIVE.contains("dev")) {
                helper.setTo("MakhrovSS1@develop.mos.ru");
            } else {
                helper.setTo(email);
            }

            helper.setSubject("Система документооборота АИС 'Тариф'");

            this.emailSender.send(message);
        }
        catch (Exception e) {

        }
    }

    public void sendExecutionEmail(String email, int docId, String regNum) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "На исполнение в АИС 'Тариф' поступил документ №" +
                    "<a href='" + uri + "/agree-document?id=" + docId +
                    "'>" + regNum + "</a>";

            message.setContent(htmlMsg, "text/html; charset=UTF-8");

            if (SPRING_PROFILES_ACTIVE.contains("dev")) {
                helper.setTo("MakhrovSS1@develop.mos.ru");
            } else {
                helper.setTo(email);
            }

            helper.setSubject("Система документооборота АИС 'Тариф'");

            this.emailSender.send(message);
        }
        catch (Exception e) {

        }
    }
}
