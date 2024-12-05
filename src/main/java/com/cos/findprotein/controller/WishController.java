package com.cos.findprotein.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.WishItem;
import com.cos.findprotein.service.WishService;

@Controller
public class WishController {

	@Autowired
	private WishService wishService;

	// 위시리스트 페이지
	@GetMapping("/wish/wishListForm")
	public String wishListForm(Model model, @AuthenticationPrincipal PrincipalDetail principal) {

		List<WishItem> wishItemList = wishService.위시리스트불러오기(principal); // 해당 사용자의 위시리스트에 담긴 상품 모두 호출

		model.addAttribute("wishItems", wishItemList);
		return "wish/wishListForm";
	}
}
