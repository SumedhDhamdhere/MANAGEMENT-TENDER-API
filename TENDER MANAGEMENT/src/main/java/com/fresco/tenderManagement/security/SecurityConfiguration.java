package com.fresco.tenderManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fresco.tenderManagement.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
(prePostEnabled = true)
public class SecurityConfiguration extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationFilter authFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Method [configure] started in SecurityConfiguration class".toUpperCase()+auth.toString());  // Green tag
        auth.userDetailsService(loginService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("Method [configure] started in SecurityConfiguration class (WebSecurity)".toUpperCase()+web.toString());  // Green tag
        web.ignoring().antMatchers("/h2-console/**", "/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Method [configure] started in SecurityConfiguration class (HttpSecurity)".toUpperCase()+http.toString());  // Green tag
        http.csrf().disable()
            .authorizeRequests()
           .antMatchers("/login").permitAll()
            .antMatchers("/bidding/add").hasAuthority("BIDDER")
            .antMatchers("/bidding/update/**").hasAuthority("APPROVER")
            .antMatchers("/bidding/list", "/bidding/delete/**").hasAnyAuthority("BIDDER", "APPROVER")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Method [passwordEncoder] started in SecurityConfiguration class".toUpperCase());  // Green tag
        return NoOpPasswordEncoder.getInstance();  // For simplicity in this example.
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        logger.info("Method [authenticationManager] started in SecurityConfiguration class".toUpperCase());  // Green tag
        return super.authenticationManager();
    }
}
