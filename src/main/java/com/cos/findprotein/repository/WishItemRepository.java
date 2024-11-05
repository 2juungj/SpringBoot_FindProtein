package com.cos.findprotein.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.findprotein.model.WishItem;

public interface WishItemRepository extends JpaRepository<WishItem, Integer> {
	Optional<WishItem> findByWishIdAndItemId(int wishId, int itemId);

	int countByWishId(int wishId); // wishId에 대한 WishItem 개수 카운트
}