package com.fresco.tenderManagement.controller;

import com.fresco.tenderManagement.dto.LoginDTO;
import com.fresco.tenderManagement.security.JWTUtil;
import com.fresco.tenderManagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginDTO loginDTO) {
        // Hardcoded log for method entry in green
        logger.info("Method [authenticateUser] started in LoginController class with email: {}".toUpperCase(), loginDTO.getEmail());

        try {
            // Authenticating user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            // Hardcoded log for successful authentication in green
            logger.info("Method [authenticateUser] - Authentication successful for email: {}".toUpperCase(), loginDTO.getEmail());

            // Generating JWT token
            UserDetails userDetails = loginService.loadUserByUsername(loginDTO.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);
            // Hardcoded log for JWT token generation in green
            logger.info("Method [authenticateUser] - JWT token generated for email: {}".toUpperCase(), loginDTO.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwt);
            response.put("status", HttpStatus.OK.value());

            // Hardcoded log for method completion in green
            logger.info("Method [authenticateUser] completed successfully in LoginController class.".toUpperCase()+loginDTO.getEmail()+loginDTO.getPassword());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Hardcoded log for failed authentication in green
            logger.info("Method [authenticateUser] - Authentication failed for email: {}. Invalid credentials.".toUpperCase(), loginDTO.getEmail()+loginDTO.getPassword());
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Hardcoded log for errors in green
            logger.info("Method [authenticateUser] - Error occurred during authentication for email: {}: {}".toUpperCase(), loginDTO.getEmail(), e.getMessage()+loginDTO.getEmail()+loginDTO.getPassword());
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
