package ru.gbuac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


@SpringBootApplication
public class Esrd2 extends SpringBootServletInitializer {

    @Autowired
    MailService mailService;

    @Override
    public void run(String...args) throws Exception {
        try {
            mailService.sendSimpleMail("dummy.newmailbox@gmail.com", "esrd.noreply@gmail.com", "Hello", "How are you");
            mailService.sendHttpMail("dummy.newmailbox@gmail.com", "esrd.noreply@gmail.com", "Hello", "<b><i><u>How are you</u></i></b>");
            System.out.println("Done");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Esrd2.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Esrd2.class, args);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }
}
