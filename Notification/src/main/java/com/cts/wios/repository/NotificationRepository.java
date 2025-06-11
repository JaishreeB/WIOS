package com.cts.wios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.wios.dto.AdminNotificationRequest;

@Repository
public interface NotificationRepository extends JpaRepository<AdminNotificationRequest, Integer> {

}
