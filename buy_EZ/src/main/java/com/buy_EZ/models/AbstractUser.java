package com.buy_EZ.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
@MappedSuperclass
public class AbstractUser {
   
	
	@NotNull(message = "Username cannot be null")
	@NotBlank(message = "Username cannot be empty")
	@Size(min = 3, max = 20, message = "Length of the username cannot be less than 3 and greater than 20")
	private String username;
	
	@NotNull
	@NotBlank
	private String firstName;
	@NotNull
	@NotBlank
	private String lastName;
	
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, max = 16, message = "Password must of the length between 8 and 16")
	private String password;
	
	@NotNull(message = "Address cannot be null")
//	@NotBlank(message = "Address cannot be empty")
//	@Size(min = 5, max = 225, message = "Length of the address must be greater than 5")
	@Embedded
	private Address address;
	
	@NotNull(message = "Mobile number cannot be null")
	@NotBlank(message = "Mobile number cannot be empty")
	@Size(min = 10, max = 10, message = "Length of the mobile number must be 10")
	private String mobileNumber;
	
	@NotNull(message = "Email cannot be null")
	@NotBlank(message = "Email cannot be empty")
	@Size(min = 7, message = "Length of the email must be greater than 7")
	@Email
	private String email;
	@NotNull
	@NotBlank
	private String country;

	
}
