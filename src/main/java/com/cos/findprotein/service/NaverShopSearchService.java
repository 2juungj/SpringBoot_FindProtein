package com.cos.findprotein.service;

import com.cos.findprotein.model.Item;
import com.cos.findprotein.model.NaverShopSearchItem;
import com.cos.findprotein.repository.ItemRepository;
import com.cos.findprotein.repository.NaverShopSearchItemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverShopSearchService {

	@Autowired
	private NaverShopSearchItemRepository naverShopSearchItemRepository;
	@Autowired
	private ItemRepository itemRepository;

	public void searchNaverShop(Item item) throws Exception {
		// RestTemplate 생성
		RestTemplate restTemplate = new RestTemplate();

		// 요청 URL 설정
		String url1 = "https://openapi.naver.com/v1/search/shop.json?query=";
		String name = item.getName();
		String url2 = "&exclude=used,rental&sort=asc";
		String reqUrl = url1 + name + url2;

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", "QOPxVkrtfan4ZXZkJYn3");
		headers.set("X-Naver-Client-Secret", "CUlseQ_TQZ");

		// 빈 요청 본문 설정
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		// GET 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, String.class);

		// JSON 응답 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response.getBody());
		JsonNode itemsNode = rootNode.path("items");
		
		// 기존의 NaverShopSearchItem 삭제
        naverShopSearchItemRepository.deleteByItemId(item.getId());

		// 각각의 아이템을 NaverShopSearchItem에 매핑
		for (JsonNode itemNode : itemsNode) {
			NaverShopSearchItem nssItem = new NaverShopSearchItem();
			nssItem.setName(itemNode.path("title").asText());
			nssItem.setLink(itemNode.path("link").asText());
			nssItem.setPrice(itemNode.path("lprice").asInt());
			nssItem.setImage(itemNode.path("image").asText());
			nssItem.setMallName(itemNode.path("mallName").asText());
			nssItem.setItem(item);

			// DB에 저장
			naverShopSearchItemRepository.save(nssItem);
		}

		// 리스트의 첫번째 이미지를 해당 상품의 이미지 변수에 담는다.
		if (itemsNode.isArray() && itemsNode.size() > 0) {
			JsonNode firstItemNode = itemsNode.get(0); // 첫 번째 아이템
			String firstImage = firstItemNode.path("image").asText(); // 첫 번째 이미지 URL
			item.setImage(firstImage); // 해당 Item의 image에 이미지 URL 저장
			
			// DB에 저장
			itemRepository.save(item);
		}
	}
}
