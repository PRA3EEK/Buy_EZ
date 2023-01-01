package com.buy_EZ.securityConfig;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.buy_EZ.jwt.AuthEntryPointJwt;
import com.buy_EZ.jwt.AuthTokenFilter;

@Configuration
@EnableGlobalMethodSecurity(
	    prePostEnabled = true)
public class ConfigClass{

	@Autowired
	private CustomeUserDetailService userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	   AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	  }
	

	@Bean
	   DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }
	  
	  @Bean
	   AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }

	
	@Bean
	SecurityFilterChain httpRequestHandling(HttpSecurity http) throws Exception
	{
		http.cors().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
	    .and()
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeHttpRequests((auth) -> 
        
			auth.
			antMatchers("/login/customer/login").permitAll()
			.antMatchers("/admin/admin/{loggedInAdminId}").hasAuthority("admin")
		       
			
		).csrf().disable().httpBasic();
		
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}
