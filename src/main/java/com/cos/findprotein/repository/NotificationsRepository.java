package com.cos.findprotein.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
	int countByNotificationId(int notificationId); // notificationId에 대한 notifications 개수 카운트
	void deleteByNotificationId(int notificationId); // 해당 notificationId를 가진 모든 notifications 삭제
}