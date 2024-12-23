package com.cos.findprotein.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class httpControllerTest {
	
	private static final String TAG  = "httpControllerTest : ";
	
	// localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@gmail.com").build();	// @Builder를 사용
		System.out.println(TAG+"getter :" + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter :" + m.getUsername());		// @Data로 Getter, Setter 사용
		return "lombok test 완료";
	}

	//인터넷 브라우저 요청은 무조건 get 요청 밖에 할 수 없다.
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청: " + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post") // text/plain,application/json
	public String postTest(@RequestBody Member m) { //MessageConverter (스프링부트)
		return "post 요청:" + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
