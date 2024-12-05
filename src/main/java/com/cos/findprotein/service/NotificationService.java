package com.cos.findprotein.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.Notification;
import com.cos.findprotein.model.NotificationType;
import com.cos.findprotein.model.Notifications;
import com.cos.findprotein.model.User;
import com.cos.findprotein.model.Wish;
import com.cos.findprotein.model.WishItem;
import com.cos.findprotein.repository.NotificationRepository;
import com.cos.findprotein.repository.NotificationsRepository;
import com.cos.findprotein.repository.UserRepository;
import com.cos.findprotein.repository.WishItemRepository;
import com.cos.findprotein.repository.WishRepository;

import jakarta.mail.MessagingException;

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
	@Autowired
	private WishRepository wishRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	// 신규 최저가가 더 저렴하면 알림과 이메일을 발송
	@Transactional
	public void 최저가알림발송(Item item, int oldLowestPrice, int newLowestPrice) throws MessagingException {

		// 신규 최저가가 더 저렴한지 확인
		if (oldLowestPrice <= newLowestPrice) {
			System.out.println("itemId: " + item.getId() + ", " + "상품명: " + item.getName()
					+ "의 신규 최저가가 더 저렴하지 않으므로 알림을 발송하지 않습니다.");
			return;
		}

		List<User> users = userRepository.findAll(); // 모든 유저를 불러온다.

		List<User> notificationAgreeUsers = new ArrayList<>(); // NotificationType이 YES인 user를 담을 리스트

		List<User> emailAgreeUsers = new ArrayList<>(); // EmailNotificationType이 YES인 user를 담을 리스트

		// NotificationType이 YES인 user를 notificationAgreeUsers에 담는다.
		for (User user : users) {
			if (user.getNotificationType() == NotificationType.YES) {
				System.out.println("ID: " + user.getId() + " NotificationType: " + user.getNotificationType());
				notificationAgreeUsers.add(user);
			}
		}

		// 필터링된 유저에 대한 작업
		for (User user : notificationAgreeUsers) {
			// builder에 사용될 notification를 찾고 없다면 생성
			Notification notification = notificationRepository.findByUserId(user.getId()).orElseGet(() -> {
				System.out.println("id: " + user.getId() + " 유저의 notification을 생성합니다.");
				Notification newNotification = Notification.builder().user(user).count(0).build();
				return notificationRepository.save(newNotification);
			});

			Wish wish = wishRepository.findByUserId(user.getId()).orElse(null);
			if (wish == null) {
				System.out.println("위시 불러오기 실패: 유저 ID " + user.getId());
				continue; // wish가 null이면 다음 사용자로 넘어감
			}

			WishItem userWishItem = wishItemRepository.findByWishIdAndItemId(wish.getId(), item.getId()).orElse(null);
			if (userWishItem == null) {
				System.out.println("위시상품 불러오기 실패: 위시 ID " + wish.getId() + ", 상품 ID " + item.getId());
				continue; // userWishItem이 null이면 다음 사용자로 넘어감
			}

			Date createTime = new Date();

			String content = "위시리스트에 등록된 " + userWishItem.getItem().getName() + "의 최저가 " + newLowestPrice
					+ "원이 등록되었습니다."; // 알림 내용

			String link = "http://localhost:8000/item/" + item.getId();

			Notifications notifications = Notifications.builder().notification(notification).content(content).link(link)
					.createTime(createTime).build();

			notificationsRepository.save(notifications); // 생성된 알림 저장

			int notificationCount = notificationsRepository.countByNotificationId(notification.getId());
			notification.setCount(notificationCount); // Notification에 담긴 Notifications의 총 개수 증가

			notificationRepository.save(notification);
		}

		// EmailNotificationType이 YES인 user를 emailAgreeUsers에 담는다.
		for (User user : users) {
			if (user.getNotificationType() == NotificationType.YES) {
				System.out
						.println("ID: " + user.getId() + " EmailNotificationType: " + user.getEmailNotificationType());
				emailAgreeUsers.add(user);
			}
		}

		// 이메일 알림 발송
		for (User user : emailAgreeUsers) {
			Wish wish = wishRepository.findByUserId(user.getId()).orElse(null);
			if (wish == null) {
				System.out.println("위시 불러오기 실패: 유저 ID " + user.getId());
				continue; // wish가 null이면 다음 사용자로 넘어감
			}

			WishItem userWishItem = wishItemRepository.findByWishIdAndItemId(wish.getId(), item.getId()).orElse(null);
			if (userWishItem == null) {
				System.out.println("위시상품 불러오기 실패: 위시 ID " + wish.getId() + ", 상품 ID " + item.getId());
				continue; // userWishItem이 null이면 다음 사용자로 넘어감
			}

			String subject = "[득근득근] 위시리스트에 등록된 상품 " + item.getName() + "이 더 저렴해졌어요!";
			String username = user.getUsername();
			String userEmail = user.getEmail();
			String itemName = item.getName();
			int lowestPrice = newLowestPrice;
			String link = "http://localhost:8000/item/" + item.getId();

			// 이메일 본문 내용
			String message = "안녕하세요, " + username + "님!\n\n" + "위시리스트에 등록된 상품 '" + itemName + "' 의 최저가 " + lowestPrice
					+ "원이 등록되었습니다.\n\n" + "자세한 내용을 확인하려면 아래 링크를 클릭해주세요:\n" + link;

			// 이메일 발송
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(userEmail);
			email.setSubject(subject);
			email.setText(message);
			email.setFrom("dgdg@gmail.com"); // 발신 이메일 설정

			// 이메일 전송
			javaMailSender.send(email);
			System.out.println("이메일 발송 완료: " + userEmail);
		}
	}

	@Transactional
	public Notification 알림불러오기(PrincipalDetail principal) {
		Notification userNotification = notificationRepository.findByUserId(principal.getUser().getId())
				.orElseGet(() -> {
					System.out.println("id: " + principal.getUser().getId() + " 유저의 notification을 생성합니다.");
					Notification newNotification = Notification.builder().user(principal.getUser()).count(0).build();
					return notificationRepository.save(newNotification);
				});

		return userNotification;
	}

	@Transactional(readOnly = true)
	public List<Notifications> 알림리스트불러오기(Notification userNotification) {

		List<Notifications> userNotificationsList = new ArrayList<>(); // 사용자의 notificationId와 일치하는 notifications를 담을
																		// 리스트

		List<Notifications> NotificationsList = notificationsRepository.findAll(); // notificationId와 상관없이 DB의 모든 알림
																					// 불러오기

		// 사용자의 wishId와 일치한 wishItem을 userWishItemList에 담기
		for (Notifications notifications : NotificationsList) {
			if (notifications.getNotification().getId() == userNotification.getId()) {
				userNotificationsList.add(notifications);
			}
		}

		// 리스트를 역순으로 정렬 (최신 알림이 상단으로)
		Collections.reverse(userNotificationsList);

		return userNotificationsList;
	}

	// 하나의 알림 삭제
	@Transactional
	public void 알림삭제(int id, PrincipalDetail principal) {
		notificationsRepository.deleteById(id);

		Notification notification = notificationRepository.findByUserId(principal.getUser().getId()).orElseThrow(() -> {
			return new IllegalArgumentException("Notification 불러오기 실패: 아이디를 찾을 수 없음.");
		});

		notification.setCount(notification.getCount() - 1); // Notification에 담긴 Notifications의 총 개수 감소

		notificationRepository.save(notification);
	}

	// 모든 알림 삭제
	@Transactional
	public void 모든알림삭제(PrincipalDetail principal) {
		// 현재 사용자의 Notification을 가져옴
		Notification notification = notificationRepository.findByUserId(principal.getUser().getId()).orElseThrow(() -> {
			return new IllegalArgumentException("Notification 불러오기 실패: 아이디를 찾을 수 없음.");
		});

		// Notification과 연관된 모든 Notifications 삭제
		notificationsRepository.deleteByNotificationId(notification.getId());

		// Notification의 알림 개수 초기화
		notification.setCount(0);

		// 변경된 Notification 저장
		notificationRepository.save(notification);
	}

}
