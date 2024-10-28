package com.cos.findprotein.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cos.findprotein.model.Item;
import com.cos.findprotein.repository.ItemRepository;

@Service
public class PriceUpdateSchedulerService {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private NaverShopSearchService naverShopSearchService;

	// 1시간마다 최저가 갱신 작업 실행
	@Scheduled(fixedRate = 3600000) // 1시간 = 3600000 밀리초
	public void updateLowestPrice() {
		System.out.println("최저가 갱신 중...");

		// 모든 Item 불러오기
		List<Item> items = itemRepository.findAll();

		// 각 Item의 최저가 갱신
		for (Item item : items) {
			try {
				naverShopSearchService.searchNaverShop(item);
			} catch (Exception e) {
				System.err.println("최저가 갱신 중 오류 발생 - Item ID: " + item.getId());
				e.printStackTrace();
			}
		}
		System.out.println("상품 최저가를 갱신하였습니다.");
	}

}
