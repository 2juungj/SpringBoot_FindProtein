package com.cos.findprotein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.User;
import com.cos.findprotein.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional
	public void 상품등록(Item item, User user) {
		// 빌더를 사용하여 객체 생성
		Item newItem = Item.builder()
											.name(item.getName())
											.info(item.getInfo())
											.category(item.getCategory())
											.user(user)
											.image(item.getImage())
											.build();

		// 객체 저장
		itemRepository.save(newItem);
	}
}
	
	
