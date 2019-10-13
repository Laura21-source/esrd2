package ru.gbuac.service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import ru.gbuac.model.User;

public interface MailService {
//    void sendEmail(User user);

    void sendSimpleEmail(String toAddress, String fromAddress, String subject, String content) throws MessagingException, javax.mail.MessagingException;

//    void setEmail(String s);

    void sendHtmlEmail(String toAddress, String fromAddress, String subject, String content) throws MessagingException, javax.mail.MessagingException;

}
