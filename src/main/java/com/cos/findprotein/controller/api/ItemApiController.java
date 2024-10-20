package com.cos.findprotein.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.dto.ResponseDto;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.service.ItemService;

@RestController
public class ItemApiController {
	
	@Autowired
	private ItemService itemService;

	// 상품 등록 (POST)
	@PostMapping("/admin/addItem")
	public ResponseDto<Integer> addItem(@RequestBody Item item, @AuthenticationPrincipal PrincipalDetail principal) throws Exception {
		itemService.상품등록(item, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
