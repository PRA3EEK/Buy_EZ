package com.buy_EZ.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerCurrentSession {
    @Id
	private String id;
	private String username;
	private String password;
	private LocalDateTime loggedInTimeStamp;
	
}
