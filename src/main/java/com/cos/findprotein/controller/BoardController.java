package com.cos.findprotein.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.Notification;
import com.cos.findprotein.model.Notifications;
import com.cos.findprotein.service.BoardService;
import com.cos.findprotein.service.ItemService;
import com.cos.findprotein.service.NotificationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private HttpSession session;

	@GetMapping({ "", "/" })
	public String index(Model model,
			@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal PrincipalDetail principal) {
		Notification userNotification = null;
		// 로그인 여부 확인
		if (principal != null && principal.getUser() != null) {
			userNotification = notificationService.알림불러오기(principal);
			List<Notifications> notificationsList = notificationService.알림리스트불러오기(userNotification);
			// 세션에 notification과 notifications의 리스트를 저장(다른 페이지에서도 알림 확인 가능)
			session.setAttribute("notification", userNotification);
			session.setAttribute("notificationsList", notificationsList);
		}

		model.addAttribute("itemList", itemService.글목록(pageable));
		return "index"; // viewResolver 작동
	}

	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail"; // 상세보기 페이지
	}

	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}

	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
