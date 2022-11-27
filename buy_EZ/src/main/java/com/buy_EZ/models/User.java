package com.buy_EZ.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractUser{
	@Id
	private String userId;
	@OneToMany(mappedBy = "user")@JsonIgnore
	private List<Order> orders = new ArrayList<>();
	@OneToOne@JsonIgnore
	private Cart cart;
	
	

}
