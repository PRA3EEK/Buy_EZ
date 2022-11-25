package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
@NoArgsConstructor

public class Admin extends AbstractUser{
    
	@Id
	private String adminId;

	public Admin(@NotNull(message = "Username cannot be null") String username,
			@NotNull(message = "Password cannot be null") String password,
			@NotNull(message = "Address cannot be null") Address address,
			@NotNull(message = "Mobile number cannot be null") String mobileNumber,
			@NotNull(message = "Email cannot be null") String email) {
		super(username, password, address, mobileNumber, email);
	
	}
	
	
	
}
