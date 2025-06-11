package com.cts.wios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.AdminNotificationRequest;
import com.cts.wios.dto.VendorNotificationRequest;
import com.cts.wios.repository.NotificationRepository;
@Service
public class NotificationServiceImpl implements NotificationService{

    private final JavaMailSender mailSender;


    private String fromEmail;

    @Autowired
    NotificationRepository repository;
    
    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendAdminNotification(AdminNotificationRequest request) {
        String subject = "Low Stock Alert: " + request.getStockName();
        String message = String.format(
            "Stock '%s' is low (Quantity: %d).\nVendor: %s (%s)\nZone: %s",
            request.getStockName(),
            request.getStockQuantity(),
            request.getVendorName(),
            request.getVendorEmail(),
            request.getZoneName()
            
        );
        repository.save(request);
        

        for (String email : request.getAdminEmails()) {
        	sendMail(email, subject, message);
        }
    }
    @Override
    public void sendVendorNotification(VendorNotificationRequest request) {
        String subject = "Low Stock Alert for Your Product";
        String message = String.format(
            "Your stock '%s' is running low. Please restock soon.",
            request.getStockName()
        );

        sendMail(request.getVendorEmail(), subject, message);
    }
    @Override
	public void sendMail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);
        mailSender.send(mail);
    }
	
}