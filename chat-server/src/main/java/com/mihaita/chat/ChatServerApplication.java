package com.mihaita.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@EnableAutoConfiguration  
@SpringBootApplication
public class ChatServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChatServerApplication.class, args);
	}
}
