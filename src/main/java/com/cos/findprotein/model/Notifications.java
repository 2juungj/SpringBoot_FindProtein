package com.cos.findprotein.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	
	@Temporal(TemporalType.TIMESTAMP)  // 필드가 Date 타입일 경우 추가
    private Date createTime;  // 알림이 발생 된 시간 // LocalDateTime 대신 Date 사용

	@Builder
	public Notifications(Notification notification, String content, String link, Date createTime) {
		this.notification = notification;
		this.content = content;
		this.link = link;
		this.createTime = createTime;
	}

}