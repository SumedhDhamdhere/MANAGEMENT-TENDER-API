package com.fresco.tenderManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fresco.tenderManagement.service.LoginService;

@SpringBootApplication
public class TenderManagementApplication {

	public static void main(String[] args) {
	
		final Logger logger = LoggerFactory.getLogger(TenderManagementApplication.class);
		logger.info("Method: Main, Class: TenderManagementApplication".toUpperCase());
		SpringApplication.run(TenderManagementApplication.class, args);
	}

}
