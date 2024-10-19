package com.cos.findprotein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

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
}
