package com.cos.findprotein.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.NaverShopSearchItem;

public interface NaverShopSearchItemRepository extends JpaRepository<NaverShopSearchItem, Integer> {
	void deleteByItemId(int itemId); // itemId로 NaverShopSearchItem 삭제
}
