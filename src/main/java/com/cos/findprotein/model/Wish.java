package com.cos.findprotein.model;

import java.util.ArrayList;
import java.util.List;

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
public class Wish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user; // 해당 유저의 위시리스트

	private int count; // 위시리스트에 담긴 총 상품 개수

	@OneToMany(mappedBy = "wish")
	private List<WishItem> wishItems = new ArrayList<>();

	@Builder
	public Wish(User user, int count) {
		this.user = user;
		this.count = count;
	}
}