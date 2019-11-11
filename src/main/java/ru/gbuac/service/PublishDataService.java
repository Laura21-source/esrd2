package ru.gbuac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.gbuac.util.DateTimeUtil;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PublishDataService {
    @Autowired
    public JavaMailSender emailSender;

    private static List<String> SPRING_PROFILES_ACTIVE;

    @Autowired
    public PublishDataService(@Value("${spring.profiles.active}") String[] actProfiles) {
        SPRING_PROFILES_ACTIVE = Arrays.asList(actProfiles);
    }

    @Value("${email.sender}")
    private String sender;


    @Value("${email.login}")
    private String login;

    public void publish(String regNum, String regDate, String publishNameMask, String publishClassifierParams) {
        if (publishClassifierParams != null) {
            String docName = String.format(publishNameMask, regNum, regDate);
            String[] topLevelClassifierParams = publishClassifierParams.split("\\|");

            String[] mosRuClassifierParams = topLevelClassifierParams[0].split(";");
            publishMosRu(docName, DateTimeUtil.toString(LocalDate.now()), mosRuClassifierParams[0], mosRuClassifierParams[1]);

            //String[] baseRegClassifierParams = topLevelClassifierParams[1].split(";");
            //String uri = publishBaseReg();

            //String[] openDataClassifierParams = topLevelClassifierParams[2].split(";");
            //publishOpenData(uri);
        }
    }

    private void publishMosRu(String docName, String publishDate, String rubrname, String uri) {
        String email = "MakhrovSS1@develop.mos.ru";
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setFrom(new InternetAddress(sender + "<" + login + ">"));

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "<p>\n" +
                    "    <strong>Дата:&nbsp " + publishDate + "</strong>\n" +
                    "</p>\n" +
                    "<table <table style=\"border-collapse: collapse;\" border=\"1\" width=\"97%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    <a name=\"l103\"></a>\n" +
                    "                    Наименование структурного подразделения\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    ИАС «Тариф»\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td colspan=\"2\" width=\"100%\" valign=\"top\">\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Название документа/ информация\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    "+docName+"\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td colspan=\"2\" width=\"100%\" valign=\"top\">\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Размещение на сайте\n" +
                    "                    <em>\n" +
                    "                        (прописать адрес страницы сайта, на которую необходимо\n" +
                    "                        установить информацию)\n" +
                    "                    </em>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    " + rubrname + ",\n" +
                    "                    " + uri + "\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td width=\"35%\" valign=\"top\">\n" +
                    "                <p>\n" +
                    "                    Срок размещения в течение\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"64%\" valign=\"top\">\n" +
                    "                <p align=\"center\">\n" +
                    "                    <strong><u>1-х суток</u></strong>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n";

            message.setContent(htmlMsg, "text/html; charset=UTF-8");

            if (SPRING_PROFILES_ACTIVE.contains("dev")) {
                helper.setTo("MakhrovSS1@develop.mos.ru");
            } else {
                helper.setTo(email);
            }

            helper.setSubject("Заявка на размещение информации на Интернет-сайте Департамента экономической политики и развития города Москвы");

            this.emailSender.send(message);
        }
        catch (Exception e) {

        }
    }

    private String publishBaseReg() {
        return null;
    }

    private void publishOpenData(String uri) {

    }
}
