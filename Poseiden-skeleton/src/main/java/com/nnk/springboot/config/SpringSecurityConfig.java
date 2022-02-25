package com.nnk.springboot.config;

import com.nnk.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 The type Spring security config.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserServiceImpl userService;

  @Profile("prod")
  @Override
  public void configure(HttpSecurity http) throws Exception{
    http
        .authorizeRequests()
        .antMatchers("/user/**").hasAuthority("ADMIN")

        .anyRequest().authenticated()
        .and()
        .formLogin()

        .and()
        .oauth2Login()

        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"));
  }

  @Profile("prod")
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
  }

  /**
   Password encoder.

   @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
