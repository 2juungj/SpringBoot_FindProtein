package com.cos.findprotein.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	Optional<Notification> findByUserId(int id);
}
