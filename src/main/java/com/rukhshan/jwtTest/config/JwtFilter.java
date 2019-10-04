package com.rukhshan.jwtTest.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rukhshan.jwtTest.service.MyUserDetailsService;
import com.rukhshan.jwtTest.service.TokenProvider;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer")) {
			String username = null;
			token = token.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(token);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService
							.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
					if (jwtTokenUtil.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// After setting the Authentication in the context, we specify
						// that the current user is authenticated. So it passes the Spring Security
						// Configurations successfully.
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}

}
