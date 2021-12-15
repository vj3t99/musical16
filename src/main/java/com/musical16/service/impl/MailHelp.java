package com.musical16.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailHelp {
	@Autowired
    public JavaMailSender emailSender;

	public String sendHtmlEmailActive(String email,String link) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        
        String htmlMsg = "<p>Xin chào,</p>"
                + "<p>Bạn có 1 yêu cầu kích hoạt tài khoản.</p>"
                + "<p>Vui lòng click vào đường link ở bên dưới để kích hoạt : </p>"
                + "<p><a href=\"" + link + "\">Active</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn chưa sẵn sàng tham gia cùng 16musical, "
                + "hoặc bạn không có bất kì yêu cầu nào.</p>";
        
        message.setContent(htmlMsg, "text/html; charset=UTF-8");
        
        helper.setTo(email);
        
        helper.setSubject("Kích hoạt tài khoản");
        
    
        this.emailSender.send(message);

        return "Email Sent!";
    }
	public void sendHtmlEmailForgotPassword(String email, String link) throws MessagingException{
		MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        
        String htmlMsg = "<p>Xin chào,</p>"
                + "<p>Bạn có 1 yêu cầu reset mật khẩu ?</p>"
                + "<p>Vui lòng click vào đường link ở bên dưới để thay đổi mật khẩu : </p>"
                + "<p><a href=\"" + link + "\">Reset Password</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu không phải là bạn, "
                + "hoặc bạn không có bất kì yêu cầu nào.</p>";
        
        message.setContent(htmlMsg, "text/html; charset=UTF-8");
        
        helper.setTo(email);
        
        helper.setSubject("Quên Mật Khẩu");
        
    
        this.emailSender.send(message);

	}
	public String sendHtmlEmailPassword(String email,String password) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        
        String htmlMsg = "<p>Hello, "+email+"</p>"
                + "<p>Đây là mật khẩu mới của bạn : "+password+" .</p>";
        
        message.setContent(htmlMsg, "text/html; charset=UTF-8");
        
        helper.setTo(email);
        
        helper.setSubject("New Password");
        
    
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
