package com.buy_EZ.models;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data@AllArgsConstructor@NoArgsConstructor
public class UserResponse {

	private String id;
	private String username;
	private String email;
	private List<GrantedAuthority> roles;
	
}
