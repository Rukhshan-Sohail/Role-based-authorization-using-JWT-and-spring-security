package com.rukhshan.jwtTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class jwtTestApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(jwtTestApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(jwtTestApplication.class);
	}

}
