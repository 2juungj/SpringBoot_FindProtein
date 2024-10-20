package com.cos.findprotein.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.NaverShopSearchItem;
import com.cos.findprotein.repository.ItemRepository;
import com.cos.findprotein.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemRepository itemRepository;

	// 상품 등록 페이지
	@GetMapping("/admin/addItemForm")
	public String addItem(Item item) {
		return "admin/addItemForm";
	}

	// 상품 리스트 페이지
	@GetMapping("/item/itemListForm")
	public String itemListForm(Model model,
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("itemList", itemService.글목록(pageable));
		return "item/itemListForm";
	}
	
	// 상품 가격 비교 페이지
	@GetMapping("/item/{id}") // 해당 id는 itemId
	public String itemCompareForm(@PathVariable int id, Model model) {	//주소의 id 값을 파라미터로 받는다.
		
		List<NaverShopSearchItem> nssItemList = itemService.가격불러오기(id);
		
		Item item = itemRepository.findById(id).get(); // 상품명, 설명 등을 위해 필요
		
		
		model.addAttribute("nssItemList", nssItemList);
		model.addAttribute("item", item);
		return "item/itemCompareForm"; // 상세보기 페이지 (가격 비교 페이지)
	}
}
