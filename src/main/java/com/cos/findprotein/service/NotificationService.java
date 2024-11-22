package com.cos.findprotein.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.Notification;
import com.cos.findprotein.model.NotificationType;
import com.cos.findprotein.model.Notifications;
import com.cos.findprotein.model.User;
import com.cos.findprotein.model.WishItem;
import com.cos.findprotein.repository.NotificationRepository;
import com.cos.findprotein.repository.NotificationsRepository;
import com.cos.findprotein.repository.UserRepository;
import com.cos.findprotein.repository.WishItemRepository;

@Service
public class NotificationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private NotificationsRepository notificationsRepository;
	@Autowired
	private WishItemRepository wishItemRepository;

	@Transactional
	public void 최저가알림발송(Item item, int oldLowestPrice, int newLowestPrice) {
		
		// 신규 최저가가 더 저렴한지 확인
	    if (oldLowestPrice <= newLowestPrice) {
	        System.out.println("itemId: "+ item.getId() + ", "+ "상품명: " + item.getName() +"의 신규 최저가가 더 저렴하지 않으므로 알림을 발송하지 않습니다.");
	        return;
	    }
		
		List<User> users = userRepository.findAll(); // 모든 유저를 불러온다.

		List<User> agreeUsers = new ArrayList<>(); // NotificationType이 YES인 user를 담을 리스트

		// NotificationType이 YES인 user를 agreeUsers에 담는다.
		for (User user : users) {
			if (user.getNotificationType() == NotificationType.YES) {
				System.out.println("ID: " + user.getId() + " NotificationType: " + user.getNotificationType());
				agreeUsers.add(user);
			}
		}

		// 필터링된 유저에 대한 작업
		for (User user : agreeUsers) {
			// builder에 사용 될 notification를 찾고 없다면 생성
			Notification notification = notificationRepository.findByUserId(user.getId()).orElseGet(() -> {
				System.out.println("id: " + user.getId() + " 유저의 notification을 생성합니다.");
				Notification newNotification = Notification.builder().user(user).count(0).build();
				return notificationRepository.save(newNotification);
			});
			
			 WishItem userWishItem = wishItemRepository.findByWishIdAndItemId(user.getId(), item.getId()).orElseThrow(() -> {
					return new IllegalArgumentException("위시상품 불러오기 실패: 아이디를 찾을 수 없음.");
				});
			
			String content = "위시리스트에 등록된 " +  userWishItem.getItem().getName() + "의 최저가 " + newLowestPrice + "원이  등록되었습니다."; // 알림 내용

			Notifications notifications = Notifications.builder().notification(notification).content(content).build(); // builder로 notifications 생성
			

			notificationsRepository.save(notifications); // 생성된 알림 저장
			
			int notificationCount = notificationsRepository.countByNotificationId(notification.getId());
			notification.setCount(notificationCount); // Notification에 담긴 Notifications의 총 개수 증가
			
			notificationRepository.save(notification);
		}
	}

}
