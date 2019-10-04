package com.rukhshan.jwtTest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rukhshan.jwtTest.model.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> roles=new ArrayList<>();
		if (username.equals("rukhshan")) {
			roles.add(new SimpleGrantedAuthority("ADMIN"));
			roles.add(new SimpleGrantedAuthority("USER"));
			return new MyUserDetails("rukhshan", new BCryptPasswordEncoder().encode("password"), roles);
		} else if (username.equals("sohail")) {
			roles.add(new SimpleGrantedAuthority("USER"));
			return new MyUserDetails("sohail", new BCryptPasswordEncoder().encode("password"), roles);
		}
		else
			throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
