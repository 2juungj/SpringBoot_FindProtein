package com.cos.findprotein.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.findprotein.model.User;
import com.cos.findprotein.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// 스프링이 로그인 요청을 가로챌 때, username, password 2개의 변수를 가로채는데
	// password 부분 처리는 알아서 해준다.
	// username이 DB에 있는지만 확인해주면 된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("해당 사용자를 찾을 수 없음.: " + username);
		});
		return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장됨.
	}

}
