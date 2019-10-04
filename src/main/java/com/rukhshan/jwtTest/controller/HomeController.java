package com.rukhshan.jwtTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

//	@Secured("ROLE_USER")
	@GetMapping("/home")
	public String home(HttpServletRequest request) {
		String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
				"HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
				"HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			System.out.println(header+"\t"+ip);
//			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
//				return ip;
//			}
//			return request.getRemoteAddr();
		}
		System.out.println(request.getRemoteAddr());
		return null;
	}

	@RequestMapping("/app")
	public String app() {
		return "App";
	}

}
