package com.cos.findprotein.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.NaverShopSearchItem;
import com.cos.findprotein.model.User;
import com.cos.findprotein.repository.ItemRepository;
import com.cos.findprotein.repository.NaverShopSearchItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private NaverShopSearchItemRepository naverShopSearchItemRepository;
	@Autowired
	private NaverShopSearchService naverShopSearchService;

	@Transactional
	public void 상품등록(Item item, User user) throws Exception {
		// 빌더를 사용하여 객체 생성
		Item newItem = Item.builder().name(item.getName()).info(item.getInfo()).category(item.getCategory()).user(user)
				.build();

		// 객체 저장
		itemRepository.save(newItem);
		// 해당 상품 최저가 상품들 저장 + newItem에 이미지 추가
		naverShopSearchService.searchNaverShop(newItem);
	}

	@Transactional
	public void 상품수정(Item item, int id) throws Exception {
		Item updateItem = itemRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("상품 불러오기 실패: 아이디를 찾을 수 없음.");
		});

		updateItem.setName(item.getName());
		updateItem.setInfo(item.getInfo());
		updateItem.setCategory(item.getCategory());
		updateItem.setUser(item.getUser());

		itemRepository.save(updateItem);
		// 해당 상품 최저가 상품들 저장(이미 저장된 최저가들은 삭제) + newItem에 이미지 갱신
		naverShopSearchService.searchNaverShop(updateItem);
	}

	@Transactional
	public void 상품삭제(int id) {
		itemRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Page<Item> 글목록(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public List<NaverShopSearchItem> 가격불러오기(int id) {

		List<NaverShopSearchItem> nssItemList = new ArrayList<>(); // itemId와 일치하는 NaverShopSearchItem들을 담을 리스트

		List<NaverShopSearchItem> allNssItem = naverShopSearchItemRepository.findAll(); // itemId와 상관없이
																						// NaverShopSearchItem의 모든 상품
																						// 불러오기

		for (NaverShopSearchItem nssItem : allNssItem) { // itemId와 일치한 nssItem을 nssItemList에 담기
			if (nssItem.getItem().getId() == id) {
				nssItemList.add(nssItem);
			}
		}
		return nssItemList;
	}
	
	@Transactional(readOnly = true)
	public Item 상품불러오기(int id) {
		return itemRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("상품 불러오기 실패: 아이디를 찾을 수 없음.");
		});
	}
}
