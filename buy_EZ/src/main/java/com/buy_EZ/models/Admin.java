package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class Admin extends AbstractUser{
    
	@Id
	private String adminId;
	
	
	public Admin(String username, String password, Address address, String mobileNumber, String email) {
		
		this.adminId = username+"_"+RandomString.make(7);
		this.setUsername(username);
        this.setPassword(password);
        this.setAddress(address);
        this.setMobileNumber(mobileNumber);
        this.setEmail(email);
	}
}
