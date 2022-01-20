package com.course.altanto.utils;

import static com.course.altanto.constant.EmailConstant.CC_EMAIL;
import static com.course.altanto.constant.EmailConstant.DEFAULT_PORT;
import static com.course.altanto.constant.EmailConstant.EMAIL_SUBJECT;
import static com.course.altanto.constant.EmailConstant.FROM_EMAIL;
import static com.course.altanto.constant.EmailConstant.GMAIL_SMTP_SERVER;
import static com.course.altanto.constant.EmailConstant.PASSWORD;
import static com.course.altanto.constant.EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL;
import static com.course.altanto.constant.EmailConstant.SMTP_AUTH;
import static com.course.altanto.constant.EmailConstant.SMTP_HOST;
import static com.course.altanto.constant.EmailConstant.SMTP_PORT;
import static com.course.altanto.constant.EmailConstant.SMTP_STARTTLS_ENABLE;
import static com.course.altanto.constant.EmailConstant.SMTP_STARTTLS_REQUIRED;
import static com.course.altanto.constant.EmailConstant.USERNAME;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPTransport;

@Service
public class EmailService {

    public void resetPassword(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(String firstName, String token, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hola " + firstName + ", \n \n Restablece tu contraseña en la siguiente url:  http://localhost:4200/reset-password/" + token  + "\n \n Nova Support");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

  
    
    // Add user manually
    
    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createMessage(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    
    private Message createMessage(String firstName, String token, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hola " + firstName + ", \n \n El password para tu cuenta es: " + token + "\n \n Ingresa a nuestro sitio: https://portal.macropay.mx/login" + "\n \n Team SAP");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
 
    
    
    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}
