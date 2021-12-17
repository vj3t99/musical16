package com.musical16.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {


	@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("16.musical.huyhoang@gmail.com");
        mailSender.setPassword("16Musical");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
	props.put("mail.smtp.socketFactory.fallback", "true");
	props.put("mail.smtp.socketFactory.port", "587");
	props.put("mail.smtp.starttls.required", "true");
	props.put("mail.smtp.ssl.enable", "false");

        return mailSender;
	}
}
