package com.cos.findprotein.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.User;
import com.cos.findprotein.model.Wish;
import com.cos.findprotein.model.WishItem;
import com.cos.findprotein.repository.UserRepository;
import com.cos.findprotein.repository.WishRepository;
import com.cos.findprotein.service.WishService;

@Controller
public class WishController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WishRepository wishRepository;
	@Autowired
	private WishService wishService;

	// 위시리스트 페이지
	@GetMapping("/wish/wishListForm")
	public String wishListForm(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		User user = userRepository.findById(principal.getUser().getId()).orElseThrow(() -> { // 로그인 한 사용자의 id로 user 데이터 호출
			return new IllegalArgumentException("유저 데이터 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		Wish wish = wishRepository.findByUserId(user.getId()).orElseThrow(() -> { // 로그인 한 사용자의 위시리스트 호출
			return new IllegalArgumentException("위시리스트 불러오기 실패: 해당 유저 아이디를 찾을 수 없음.");
		});

		List<WishItem> wishItemList = wishService.위시리스트불러오기(wish); // 해당 사용자의 위시리스트에 담긴 상품 모두 호출

		model.addAttribute("wishItems", wishItemList);
		return "wish/wishListForm";
	}
}
