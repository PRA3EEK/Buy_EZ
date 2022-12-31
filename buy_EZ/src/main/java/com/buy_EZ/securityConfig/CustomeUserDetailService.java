package com.buy_EZ.securityConfig;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buy_EZ.models.CustomerCurrentSession;
import com.buy_EZ.models.User;
import com.buy_EZ.repositories.CustomerCurrentSessionRepo;
import com.buy_EZ.repositories.CustomerRepo;




@Service
public class CustomeUserDetailService implements UserDetailsService{

	@Autowired
	private CustomerCurrentSessionRepo sessionRepo;
	@Autowired
	private CustomerRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		CustomerCurrentSession us = sessionRepo.findByUsername(username);
		
		if(us!=null)
		{
			User u = userRepo.findById(us.getId()).get();
			CustomUserDetails userDetail = new CustomUserDetails(us, u.getRole());
			return userDetail;
		}
		throw new UsernameNotFoundException("Username not found");
	}

}
