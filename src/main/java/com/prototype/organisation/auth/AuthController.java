package com.prototype.organisation.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://192.168.178.31:4200", "http://localhost:4200"})
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@PostMapping("/login")
	public boolean login(@RequestBody Auth body, HttpServletRequest http) {
		return service.login(body, http);
	}
	
	
	@GetMapping("/session")
	public String checkSession(HttpServletRequest http, HttpServletResponse response, @CookieValue(value = "SESSION", defaultValue = "default") String testCookie) {
		/*
		response.addHeader("session-id", "test");
		response.addHeader("Access-Control-Expose-Headers", "session-id");
		*/
		/*
		Cookie cookie = new Cookie("test-cookie","hello");
		cookie.setMaxAge(60);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		*/
		try {
			System.out.println("AuthController session userID: " + http.getSession().getAttribute("USER_ID"));
			return "{\"USER_ID\":\"" + http.getSession().getAttribute("USER_ID").toString() + "\"}";
		}catch(Exception e) {
			return "{\"USER_ID\":null}";
		}
	}
	
	@GetMapping("/logout")
	public boolean logout(HttpServletRequest http) {
		try {
			http.getSession().invalidate();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	@PostMapping("/checkAuth")
	public boolean checkAuth(@RequestBody Auth body) {
		return service.checkAuth(body);
	}
}
