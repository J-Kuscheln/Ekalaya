package com.prototype.organisation.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prototype.organisation.member.Member;
import com.prototype.organisation.member.MemberRepo;

@Service
public class AuthService {
	@Autowired
	private MemberRepo memberRepo;

	public boolean login(Auth body, HttpServletRequest http) {
		Member member = memberRepo.findByEmail(body.getEmail());
		
		if (member!=null && member.getPassword().equals(body.getPassword().toString())) {
			http.getSession().setAttribute("USER_ID", member.getId().toString());
			return true;
		}
		return false;
		
	}
	
	public boolean checkAuth(Auth body) {
		Member member = memberRepo.findByEmail(body.getEmail());
		
		if(member.getPassword().equals(body.getPassword())) return true;
		
		return false;
	}
	
	
}
