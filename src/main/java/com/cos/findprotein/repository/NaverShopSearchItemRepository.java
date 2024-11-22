package com.cos.findprotein.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.NaverShopSearchItem;

public interface NaverShopSearchItemRepository extends JpaRepository<NaverShopSearchItem, Integer> {
	Optional<NaverShopSearchItem> findFirstByItemId(int itemId); // 최저가 상품만 호출
	
	void deleteByItemId(int itemId); // itemId로 NaverShopSearchItem 삭제
}
