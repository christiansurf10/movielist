package com.outsera.movielist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.outsera.movielist")
@SpringBootApplication
public class MovielistApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielistApplication.class, args);
	}

}
