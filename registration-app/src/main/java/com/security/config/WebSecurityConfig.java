package com.security.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig  {

	public static final String White_List_urls[] = {
			"/register", "/hello","/verifyToken", "/resendToken","/resetPassword","/savePassword"
	};
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(11);
		
	}

	
	@Bean 
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		 http.cors().and().csrf().disable().
				authorizeHttpRequests().antMatchers(White_List_urls).permitAll();
		 
		 return http.build();
	}
}
