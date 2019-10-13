package ru.gbuac.service;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

//    @Autowired
//    public MailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    @Override
    public void sendSimpleEmail(String toAddress, String fromAddress, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content);
        helper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);

    }


//    public void sendEmail(User user) throws MailException {
//
//        /*
//         * This JavaMailSender Interface is used to send Mail in Spring Boot. This
//         * JavaMailSender extends the MailSender Interface which contains send()
//         * function. SimpleMailMessage Object is required because send() function uses
//         * object of SimpleMailMessage as a Parameter
//         */
//
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(user.getEmail());
//        mail.setSubject("Testing Mail API");
//        mail.setText("Hurray ! You have done that dude...");
//
//        /*
//         * This send() contains an Object of SimpleMailMessage as an Parameter
//         */
//        javaMailSender.send(mail);
//    }


//    public void sendSimpleEmail(User user) throws MessagingException {
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//        helper.setTo(user.getEmail());
//        helper.setSubject("Testing Mail API with Attachment");
//        helper.setText("Please find the attached document below.");
//
//        ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
//        helper.addAttachment(classPathResource.getFilename(), classPathResource);
//
//        javaMailSender.send(mimeMessage);
//    }
}
