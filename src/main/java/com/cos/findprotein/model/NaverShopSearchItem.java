package com.cos.findprotein.model;

import jakarta.persistence.Column;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NaverShopSearchItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "itemId")
	private Item item;					// 상품 id
	
	@Column(nullable = false)
	private String name;				// 상품명
	
	@Column(nullable = false)
	private String mallName;		// 판매 사이트명
	
	@Column(nullable = false)
	private String link;					// 주소
	
	@Column(nullable = false)
	private int price;					// 가격
	
	private String image;			// 상품 이미지
	
}
