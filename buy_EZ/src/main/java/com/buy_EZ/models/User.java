package com.buy_EZ.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class User extends AbstractUser{
	@Id
	private String userId;
	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();
	@OneToOne
	private Cart cart;
	
	public User(String username, String password, Address address, String mobileNumber, String email) {
		this.userId = username+"_"+RandomString.make(7);
		this.setPassword(password);
		this.setUsername(username);
		this.setAddress(address);
		this.setMobileNumber(mobileNumber);
		this.setEmail(email);
	}

}
