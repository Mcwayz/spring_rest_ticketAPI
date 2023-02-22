package com.example.TicketingRestApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {

      return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .authorizeHttpRequests()
          .requestMatchers("/api/ticketTypes").permitAll()
          .requestMatchers("/api/ticketPrice").permitAll()
          .requestMatchers("/api/tickets/**").permitAll()
          .requestMatchers("/login", "/resources/**", "/assets/**", "/bootstrap/**", "/sass/**", "/plugins/**", "/img/**").permitAll()
          .requestMatchers("/register", "/resources/**", "/assets/**", "/bootstrap/**", "/sass/**", "/plugins/**", "/img/**").permitAll()
          .requestMatchers("/ticket/ticketTypes/**").hasAnyAuthority("ADMIN")
          .requestMatchers("/ticket/tickets/**").hasAnyAuthority("ADMIN", "SELLER", "ACCOUNTANT")
          .requestMatchers("/report/**").hasAnyAuthority("ADMIN", "ACCOUNTANT")
          .requestMatchers("/security/**").hasAuthority("ADMIN")
          .anyRequest().authenticated()
          .and()
          .formLogin()
          .loginPage("/login").permitAll()
          .defaultSuccessUrl("/index")
          .and()
          .logout().invalidateHttpSession(true)
          .clearAuthentication(true)
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/login").permitAll()
          .and()
          .exceptionHandling().accessDeniedPage("/accessDenied");
     
      return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder()
  {
    return NoOpPasswordEncoder.getInstance();
  }

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

      provider.setUserDetailsService(userDetailsService);

      provider.setPasswordEncoder(passwordEncoder());
      return provider;
  }
    
}
