package com.iitbh.ccms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CCMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(CCMSApplication.class, args);
	}

}
