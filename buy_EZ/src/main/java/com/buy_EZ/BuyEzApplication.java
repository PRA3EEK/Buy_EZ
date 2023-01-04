package com.buy_EZ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class BuyEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyEzApplication.class, args);
	}

}
