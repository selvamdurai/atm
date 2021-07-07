package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class AtmApplication {
	private static final Logger LOG
			= Logger.getLogger(AtmApplication.class.getName());



	public static void main(String[] args) {
		SpringApplication.run(AtmApplication.class, args);
	}

}
