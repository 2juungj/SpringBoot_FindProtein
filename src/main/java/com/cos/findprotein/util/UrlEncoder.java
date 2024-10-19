package com.cos.findprotein.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoder {

	// URL 인코딩 메소드
	public static String encode(String text) {
		try {
			// UTF-8로 인코딩
			return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// 인코딩 실패 시 오류 처리
			throw new RuntimeException("URL 인코딩 실패: " + e.getMessage(), e);
		}
	}
}
