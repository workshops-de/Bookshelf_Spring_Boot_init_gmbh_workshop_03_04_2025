package de.workshops.bookshelf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

@Configuration
public class MailConfiguration {

    @Bean
    Session mailSession() {
        return Session.getDefaultInstance(System.getProperties());
    }

    @Bean
    MimeMessage mimeMessage() {
        return new MimeMessage(mailSession());
    }
}
