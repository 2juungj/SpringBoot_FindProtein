package com.cos.findprotein.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	private int count; // 총 알림 개수
	
	@OneToMany(mappedBy = "notification", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Notifications> notifications = new ArrayList<>();

	@Builder
	public Notification(User user, int count) {
		this.user = user;
		this.count = count;
	}

}