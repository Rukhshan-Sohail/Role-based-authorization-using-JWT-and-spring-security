package com.rukhshan.jwtTest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rukhshan.jwtTest.service.MyUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.authorizeRequests().antMatchers(HttpMethod.POST,"/authenticate").permitAll()
			.antMatchers("/app").hasAuthority("ADMIN")
			.antMatchers("/home").hasAuthority("USER")
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
