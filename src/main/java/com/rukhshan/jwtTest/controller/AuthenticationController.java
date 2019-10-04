package com.rukhshan.jwtTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rukhshan.jwtTest.model.JwtRequest;
import com.rukhshan.jwtTest.model.JwtResponse;
import com.rukhshan.jwtTest.service.MyUserDetailsService;
import com.rukhshan.jwtTest.service.TokenProvider;

@RestController

public class AuthenticationController {
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
//		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//		final UserDetails userDetails = userDetailsService
//				.loadUserByUsername(authenticationRequest.getUsername());
		final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        System.out.println("AUTHENTICATION -> "+authentication);
//		System.out.println("USER DETAILS -> "+userDetails.getUsername()+"\n"+userDetails.getPassword()+"\n"+userDetails.getAuthorities());
		final String token = jwtTokenUtil.generateToken(authentication);
		
		System.out.println("TOKEN -> "+token);

		return ResponseEntity.ok(new JwtResponse(token));
	}
}
