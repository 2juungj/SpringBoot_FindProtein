package com.cos.findprotein.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name; // 상품명

	@Column(nullable = false)
	private String encName; // URL 인코딩 된 상품명

	private String info; // 상품 설명

	@Enumerated(EnumType.STRING)
	private Category category; // 카테고리 // PROSUPPS

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user; // 상품 등록자

	private String image; // 이미지 링크

	@Builder
	public Item(String name, String encName, String info, Category category, User user, String image) {
		this.name = name;
		this.encName = encName;
		this.info = info;
		this.category = category;
		this.user = user;
		this.image = image;
	}
}
