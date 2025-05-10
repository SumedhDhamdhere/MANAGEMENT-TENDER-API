package com.fresco.tenderManagement.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginDTO {
    private String email;
    private String password;

    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(LoginDTO.class);

    public LoginDTO() {
        logger.info("Method: LoginDTO() called from Class: LoginDTO".toUpperCase());

    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
        
    }

    public String getEmail() {
        
        return email;
    }

    public void setEmail(String email) {
      
        this.email = email;
    }

    public String getPassword() {
    
        return password;
    }

    public void setPassword(String password) {
     
        this.password = password;
    }
}
