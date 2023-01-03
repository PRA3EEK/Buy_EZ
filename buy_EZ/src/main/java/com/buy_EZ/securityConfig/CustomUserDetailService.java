package com.buy_EZ.securityConfig;





import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.buy_EZ.models.User;

import com.buy_EZ.repositories.CustomerRepo;




@Service
public class CustomUserDetailService implements UserDetailsService{


	@Autowired
	private CustomerRepo userRepo;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User us = userRepo.findByUsername(username);
		
		if(us!=null)
		{
			
			CustomUserDetails userDetail = CustomUserDetails.build(us);
			return userDetail;
		}
		throw new UsernameNotFoundException("Username not found");
	}

}
