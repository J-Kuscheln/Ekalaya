package com.prototype.organisation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class sessionController {
	
	@RequestMapping("/check/session")
	public String checkSession(HttpServletRequest httpServlet) {
		String response = "{ \"USER_ID\": " + httpServlet.getSession().getAttribute("USER_ID") + " }";
		System.out.println(response);
		return response;
	}
}
