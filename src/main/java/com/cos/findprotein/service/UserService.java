package com.cos.findprotein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.findprotein.config.auth.PrincipalDetail;
import com.cos.findprotein.model.EmailNotificationType;
import com.cos.findprotein.model.NotificationType;
import com.cos.findprotein.model.RoleType;
import com.cos.findprotein.model.User;
import com.cos.findprotein.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {

		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;
	}

	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = encoder.encode(rawPassword); // 해시
		user.setPassword(encPassword); // 비밀번호를 해시 값으로 변경
		user.setRole(RoleType.USER);
		user.setNotificationType(NotificationType.NO);
		user.setEmailNotificationType(EmailNotificationType.NO);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User user, PrincipalDetail principal) {
		// 수정 시에는 영속성 컨텍스트에 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유: 영속화하기 위해
		// 영속화 된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});

		// Validate 체크 => oauth 필드에 값이 없으면 수정 가능
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}

		// 회원수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 = 자동으로 commit
		// 영속화 된 persistance 객체의 변화가 감지 -> 더티체킹 되어 update문을 날려준다.
		principal.setUser(persistance);
	}

	@Transactional
	public void 알림설정변경(PrincipalDetail principal) {
	    User user = userRepository.findById(principal.getUser().getId()).orElseThrow(() -> {
	        return new IllegalArgumentException("회원 찾기 실패");
	    });

	    // notification 값 변경
	    if (user.getNotificationType() == NotificationType.YES) {
	        user.setNotificationType(NotificationType.NO);
	    } else if (user.getNotificationType() == NotificationType.NO) {
	        user.setNotificationType(NotificationType.YES);
	    } else {
	    	throw new IllegalStateException("유효하지 않은 notification 값입니다.");
	    }

	    userRepository.save(user); // 변경된 notification 값을 저장
	    
	    // 세션에 저장된 principal 객체의 값을 갱신
	    principal.setUser(user); // 변경된 user 객체를 principal에 반영
	}
	
	@Transactional
	public void 이메일알림설정변경(PrincipalDetail principal) {
	    User user = userRepository.findById(principal.getUser().getId()).orElseThrow(() -> {
	        return new IllegalArgumentException("회원 찾기 실패");
	    });

	    // emailNotification 값 변경
	    if (user.getEmailNotificationType() == EmailNotificationType.YES) {
	        user.setEmailNotificationType(EmailNotificationType.NO);
	    } else if (user.getEmailNotificationType() == EmailNotificationType.NO) {
	        user.setEmailNotificationType(EmailNotificationType.YES);
	    } else {
	    	throw new IllegalStateException("유효하지 않은 emailNotification 값입니다.");
	    }

	    userRepository.save(user); // 변경된 emailNotification 값을 저장
	    
	    // 세션에 저장된 principal 객체의 값을 갱신
	    principal.setUser(user); // 변경된 user 객체를 principal에 반영
	}

}
