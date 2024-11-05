package com.cos.findprotein.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.dto.ResponseDto;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.service.WishService;

@RestController
public class WishApiController {

	@Autowired
	private WishService wishService;

	@PostMapping("/wish/addToWish")
	public ResponseDto<Integer> addWish(@RequestBody Item item, @AuthenticationPrincipal PrincipalDetail principal) {
		wishService.위시리스트에추가(item, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/wish/deleteWish/{id}") // wishItemId
	public ResponseDto<Integer> deleteWish(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {
		wishService.위시상품삭제(id, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
