package com.cos.findprotein.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notificationId")
	private Notification notification;
	
	private String content; // 알림 내용
	
	private String link; // 최저가 비교 페이지 링크

	@Builder
	public Notifications(Notification notification, String content, String link) {
		this.notification = notification;
		this.content = content;
		this.link = link;
	}

}