package com.buy_EZ.securityConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.CustomerRepo;

public class CustomUserDetails implements UserDetails{


	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerCurrentSession currentSession;
	@Autowired
	private CustomerRepo userRepo;
	
	public CustomUserDetails(CustomerCurrentSession currentSession)
	{
		this.currentSession = currentSession;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> gratedAuthorities = new ArrayList<>();
		System.out.println("error");
		User user = userRepo.findById(currentSession.getId()).get();
		System.out.println("no error");
		for(String s:user.getRole())
		{
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(s);
			gratedAuthorities.add(sga);
		}
		return gratedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return currentSession.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return currentSession.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
