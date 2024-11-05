package com.cos.findprotein.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.User;
import com.cos.findprotein.model.Wish;
import com.cos.findprotein.model.WishItem;
import com.cos.findprotein.repository.ItemRepository;
import com.cos.findprotein.repository.WishItemRepository;
import com.cos.findprotein.repository.WishRepository;

@Service
public class WishService {

	@Autowired
	private WishRepository wishRepository;
	@Autowired
	private WishItemRepository wishItemRepository;
	@Autowired
	private ItemRepository itemRepository;

	@Transactional
	public void 위시리스트에추가(Item newItem, PrincipalDetail principal) {
		User user = principal.getUser();
		Wish wish = wishRepository.findByUserId(user.getId()) // 사용자 userId로 사용자 위시리스트 찾기
				.orElseGet(() -> { // 위시리스트가 없다면 생성
					Wish newWish = Wish.builder().user(user).count(0).build();
					return wishRepository.save(newWish);
				});

		Item item = itemRepository.findById(newItem.getId()).orElseThrow(() -> { // itemId로 item를 불러온다.
			return new IllegalArgumentException("상품 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		
		Optional<WishItem> existingWishItem = wishItemRepository.findByWishIdAndItemId(wish.getId(), item.getId()); // wishId와 itemId로 wishItem을 불러온다.

	    // WishItem이 존재할 경우 추가하지 않는다.
	    if (existingWishItem.isPresent()) {
	        System.out.println("이미 위시리스트에 추가된 상품입니다.");
	        return; // 리턴값을 없애고 메소드를 종료합니다.
	    }

	    // WishItem이 존재하지 않을 경우 생성
	    WishItem newWishItem = WishItem.builder().wish(wish).item(item).build();
	    wish.setCount(wishItemRepository.countByWishId(wish.getId()) + 1); // 위시리스트의 상품 수 증가
	    wishRepository.save(wish); // 위시 리스트 업데이트
	    wishItemRepository.save(newWishItem); // 새로운 WishItem 저장

	    System.out.println("상품이 위시리스트에 추가되었습니다.");
	}


	// 위시 상품 리스트를 모두 불러와 해당 사용자의 wishId와 일치하는 상품만 반환
	@Transactional(readOnly = true)
	public List<WishItem> 위시리스트불러오기(Wish wish) {

		int userWishId = wish.getId(); // 사용자의 wishId 불러오기

		List<WishItem> userWishItemList = new ArrayList<>(); // 사용자의 wishId와 일치하는 wishItem 담을 리스트

		List<WishItem> wishItemList = wishItemRepository.findAll(); // id와 상관없이 wishItem의 모든 상품 불러오기

		for (WishItem wishItem : wishItemList) { // 사용자의 wishId와 일치한 wishItem을 userWishItemList에 담기
			if (wishItem.getWish().getId() == userWishId) {
				userWishItemList.add(wishItem);
			}
		}
		
		 // 리스트를 역순으로 정렬 (최신 상품이 상단으로)
        Collections.reverse(userWishItemList);
        
		return userWishItemList;
	}
	
	@Transactional
	public void 위시상품삭제(int id, PrincipalDetail principal) {
		User user = principal.getUser();
		Wish wish = wishRepository.findByUserId(user.getId()).orElseThrow(() -> { // 사용자의 위시리스트 호출
			return new IllegalArgumentException("위시리스트 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		int wishCount = wishItemRepository.countByWishId(wish.getId()); // 해당 유저의 wishItem의 수량 불러오기
		
		WishItem wishItem = wishItemRepository.findById(id).orElseThrow(() -> { // 해당 위시 상품이 DB에 있는지 확인
			return new IllegalArgumentException("위시리스트 상품 불러오기 실패: 아이디를 찾을 수 없음.");
		});
		wishItemRepository.deleteById(id); // wishItemId로 해당 위시 상품 삭제
		wishCount -= 1; // 위시 상품이 삭제되었으므로 수량 -1
		
		wish.setCount(wishCount); // 수정된 count를 wish에 저장
		wishRepository.save(wish); // 수정된 wish를 DB에 저장
	}
}
