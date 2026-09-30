package com.zaalima.iam_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IamServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IamServerApplication.class, args);
		System.out.println("I am server is running on port 9000");
	}

}
