package com.buy_EZ.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    
	@NotNull(message = "Pincode cannot be null")
	@NotBlank(message = "Pincode cannot be empty")
	@Size(min = 6, max = 6, message = "Length of the pincode must be 6")
	private String pincode;
	
	@NotNull(message = "State cannot be null")
	@NotBlank(message = "State cannot be empty")
	@Size(min = 3, max = 50, message = "Length of the state name must be greater than 3")
	private String state;
	
	@NotNull(message = "City cannot be null")
	@NotBlank(message = "City cannot be empty")
	@Size(min = 3, max = 50, message = "Length of the city must be greater than 3")
	private String city;
	
	@NotNull(message = "Address details cannot be null")
	@NotBlank(message = "Address details cannot be empty")
	@Size(min = 3, max = 255, message = "Length of the address details must be greater than 3")
	private String details;
	
	
	
}
