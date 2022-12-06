package com.buy_EZ.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity@Data@AllArgsConstructor@NoArgsConstructor
public class Supplier{

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long supplierId;
	@NotBlank
	@NotNull
	private String companyName;
	@NotBlank
	@NotNull
	private String city;
	@NotBlank
	@NotNull
	private String state;
	@NotBlank
	@NotNull
	@Size(min = 6, max = 6)
	private String postalCode;
	@NotBlank
	@NotNull
	private String country;
	@NotBlank
	@NotNull
	@Size(min = 6, max = 15)
	private String mobileNumber;
	@NotBlank
	@NotNull
	@Email
	private String email;
	
	
	
}
