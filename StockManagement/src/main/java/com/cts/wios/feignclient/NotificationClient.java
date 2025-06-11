package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.wios.dto.AdminNotificationRequest;
import com.cts.wios.dto.VendorNotificationRequest;

@FeignClient(name = "NOTIFICATIONSERVICE", url = "http://localhost:1119")
public interface NotificationClient {

    @PostMapping("/notifications/low-stock/admin")
    void sendAdminLowStockNotification(@RequestBody AdminNotificationRequest request);

    @PostMapping("/notifications/low-stock/vendor")
    void sendVendorLowStockNotification(@RequestBody VendorNotificationRequest request);
}
 
