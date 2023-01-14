package com.buy_EZ;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import antlr.collections.List;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class BuyEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyEzApplication.class, args);
	}
  
}
