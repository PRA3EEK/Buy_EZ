package com.buy_EZ.securityConfig;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigClass{

	@Bean
	SecurityFilterChain httpRequestHandling(HttpSecurity http) throws Exception
	{
		http.cors().disable().authorizeHttpRequests((auth) -> 
        
			auth.antMatchers("/login/customer/login").permitAll()
			.antMatchers("/admin/admin/{loggedInAdminId}").hasAuthority("admin")
			   
			
		).csrf().disable().httpBasic();
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder customPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
}
