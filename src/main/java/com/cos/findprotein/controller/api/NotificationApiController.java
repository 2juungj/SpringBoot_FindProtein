package com.cos.findprotein.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.dto.ResponseDto;
import com.cos.findprotein.service.NotificationService;

@RestController
public class NotificationApiController {
	@Autowired
	private NotificationService notificationService;

	// 알림 삭제 (DELETE)
	@DeleteMapping("/notification/deleteNotifications/{id}")
	public ResponseDto<Integer> deleteNotifications(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal){
		notificationService.알림삭제(id, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 모든 알림 삭제 (DELETE)
	@DeleteMapping("/notification/deleteAllNotifications")
	public ResponseDto<Integer> deleteAllNotifications(@AuthenticationPrincipal PrincipalDetail principal){
		notificationService.모든알림삭제(principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
