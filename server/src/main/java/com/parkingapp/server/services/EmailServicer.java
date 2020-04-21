package com.parkingapp.server.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailServicer")	
@Configuration	
@PropertySource(value = "classpath:application.properties")	
public class EmailServicer {		

	@Autowired	
	private JavaMailSenderImpl sender;	

	// @Bean	
	public void sendEmail(String login, String email, String password) throws Exception {	
        MimeMessage message = sender.createMimeMessage();	
        MimeMessageHelper helper = new MimeMessageHelper(message);		
        helper.setTo(email);	
        // helper.setText("Welcome, \n Username: " + login + "\n Password: " + password + "\n Please login to change your password");	
        String htmlMsg = "<body style='border:2px solid black'>"
                    +"Your login is " + login + " and your onetime password for registration is " + password + ". "
                        + "Please use this OTP to complete your new user registration."+
                          "OTP is confidential, do not share this  with anyone.</body>";
        helper.setText(htmlMsg, true);
        helper.setSubject("Registration details");		
        sender.send(message);	
    }	

    public void parkingConfirmationEmail(String username, String email, String userUrl, String url) throws Exception {	
        MimeMessage message = sender.createMimeMessage();	
        MimeMessageHelper helper = new MimeMessageHelper(message);		
        helper.setTo(email);	
        // helper.setText("Welcome, \n Username: " + login + "\n Password: " + password + "\n Please login to change your password");	
        String htmlMsg = "<body style='border:2px solid black'>"
                    +"Your confirmation URL is "+ url+ userUrl + "</body>";
        helper.setText(htmlMsg, true);
        helper.setSubject("Booking confirmation");		
        sender.send(message);	
    }	
} 