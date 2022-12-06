package com.buy_EZ.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity@Data@AllArgsConstructor@NoArgsConstructor
public class Shipper {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long shipperid;
	@NonNull
	@NotBlank
	private String companyName;
    @NonNull
    @NotBlank
    @Size(min = 10, max = 10, message = "length of the number must be 10")
	private String mobileNumber;
	@OneToMany(mappedBy = "shipper")@JsonIgnore
	private List<Order> orders = new ArrayList<>();
	
}
