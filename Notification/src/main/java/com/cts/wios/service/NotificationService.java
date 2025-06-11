package com.cts.wios.service;

import com.cts.wios.dto.AdminNotificationRequest;
import com.cts.wios.dto.VendorNotificationRequest;

//Service interface for handling notification-related operations
public interface NotificationService {

	public abstract void sendMail(String toMail, String subject, String body);

	public abstract void sendAdminNotification(AdminNotificationRequest request);

	public abstract void sendVendorNotification(VendorNotificationRequest request);
}