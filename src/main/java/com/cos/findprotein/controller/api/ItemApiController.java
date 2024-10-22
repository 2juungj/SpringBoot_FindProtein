package com.cos.findprotein.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseDto<Integer> addItem(@RequestBody Item item, @AuthenticationPrincipal PrincipalDetail principal)
			throws Exception {
		itemService.상품등록(item, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 상품 수정 (PUT)
	@PutMapping("/admin/updateItem/{id}")
	public ResponseDto<Integer> updateItem(@RequestBody Item item, @PathVariable int id) throws Exception {
		itemService.상품수정(item, id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 상품 최저가 갱신 (DELETE, PUT)
	@PutMapping("/admin/renewItem/{id}")
	public ResponseDto<Integer> renewItem(@PathVariable int id) throws Exception {
		itemService.상품최저가갱신(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 상품 삭제 (DELETE)
	@DeleteMapping("/admin/deleteItem/{id}")
	public ResponseDto<Integer> deleteItem(@PathVariable int id) {
		itemService.상품삭제(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
