package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private UserServiceImpl service;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args){

		User user= new User();
		user.setUsername("userTest");
		user.setFullName("UserFullName");
		user.setPassword("user123");
		user.setRole("USER");

		service.create(user);

		User user1= new User();
		user1.setUsername("adminTest");
		user1.setFullName("AdminFullName");
		user1.setPassword("admin123");
		user1.setRole("ADMIN");

		service.create(user1);
	}
}
