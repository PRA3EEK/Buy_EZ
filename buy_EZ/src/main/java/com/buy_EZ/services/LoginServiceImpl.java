package com.buy_EZ.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buy_EZ.exceptions.AdminException;
import com.buy_EZ.models.Admin;
import com.buy_EZ.models.AdminCurrentSession;
import com.buy_EZ.models.AdminDto;
import com.buy_EZ.repositories.AdminCurrentSessionRepo;
import com.buy_EZ.repositories.AdminRepo;

import net.bytebuddy.utility.RandomString;


@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private AdminCurrentSessionRepo adminCurrentSession;
	
	@Override
	public AdminCurrentSession adminLogin(AdminDto admin) throws AdminException {
		// TODO Auto-generated method stub
		
Admin a = adminRepo.findByUsername(admin.getUsername());
		
		if(a!=null) {
			
			
			if(a.getPassword().equals(admin.getPassword())) {
				
				AdminCurrentSession acs =  adminCurrentSession.findByUsername(a.getUsername());
				if(acs == null) {
					AdminCurrentSession ac = new AdminCurrentSession(a.getAdminId(), a.getUsername(), RandomString.make(6), LocalDateTime.now());
					
					adminCurrentSession.save(ac);
					
					return ac;
				}
				
				throw new AdminException("Admin is already logged in");
			}else {
				throw new AdminException("Incorrect Password");
			}
			
		}
		throw new AdminException("No admin available with the username "+admin.getUsername());
		
	}

}
