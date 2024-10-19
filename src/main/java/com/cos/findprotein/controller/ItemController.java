package com.cos.findprotein.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.cos.findprotein.model.Item;

@Controller
public class ItemController {

	// 상품 등록 페이지
	@GetMapping("/admin/addItemForm")
	public String addItem(Item item) {
		return "admin/addItemForm";
	}
}
