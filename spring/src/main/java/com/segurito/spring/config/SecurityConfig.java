package com.segurito.spring.config;

import com.segurito.spring.services.UserLoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity //Enable annotations for security
@Configuration
public class SecurityConfig {

  private final UserLoginServiceImpl userLoginServiceImpl;

  public SecurityConfig(UserLoginServiceImpl userLoginServiceImpl) {
    this.userLoginServiceImpl = userLoginServiceImpl;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, UserLoginServiceImpl userLoginServiceImpl,
                                                     PasswordEncoder passwordEncoder) throws Exception {
    AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authBuilder.userDetailsService(userLoginServiceImpl).passwordEncoder(passwordEncoder);

    return authBuilder.build();
  }

  //SecurityFilterChain
  //Generates security rules for users profiles
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) //Avoid hacking
            .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/home", "/login", "/user/add").permitAll()//Public routes
                            .requestMatchers("/dashboard").hasRole("ADMIN")//Only users with ADMIN role can access these routes
                            .requestMatchers("/problems").hasAnyRole("ADMIN", "USER")//Only users with ADMIN or USER role can access these routes
                            .anyRequest().authenticated()//Private routes
            )
            .formLogin(form -> form
                            .loginPage("/login")//Login route
                            .defaultSuccessUrl("/home", true)//Redirect to this route after login
                            .permitAll()//Everyone
            )
            .logout(logout -> logout
                            .logoutUrl("/logout")//Logout route
                            .logoutSuccessUrl("/login?logout")//Route after logout
            )
            .exceptionHandling(exceptions -> exceptions
                            .accessDeniedPage("/access-denied")//Access denied route
            );
    return http.build();
  }
}
