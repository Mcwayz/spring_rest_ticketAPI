package com.example.TicketingRestApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.TicketingRestApi.security.restapi.jwtconfig.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

 
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .authorizeHttpRequests()
              // First configuration
              .requestMatchers("/login", "/resources/**", "/assets/**", "/bootstrap/**", "/sass/**", "/plugins/**", "/img/**").permitAll()
              .requestMatchers("/register", "/resources/**", "/assets/**", "/bootstrap/**", "/sass/**", "/plugins/**", "/img/**").permitAll()
              .requestMatchers("/ticket/ticketTypes/**").hasAnyAuthority("ADMIN")
              .requestMatchers("/ticket/tickets/**").hasAnyAuthority("ADMIN", "SELLER", "ACCOUNTANT")
              .requestMatchers("/report/**").hasAnyAuthority("ADMIN", "ACCOUNTANT")
              .requestMatchers("/security/**").hasAuthority("ADMIN")
              // Second configuration
              .requestMatchers("/api/v1/auth/**").permitAll()
              .anyRequest().authenticated()
          .and()
          .formLogin()
              .loginPage("/login").permitAll()
              .defaultSuccessUrl("/index")
          .and()
          .logout()
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login").permitAll()
          .and()
          .exceptionHandling().accessDeniedPage("/accessDenied")
          // .and()
          // Jwt configuration
          // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authenticationProvider(authenticationProvider)
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  
      return http.build();
  }

//   @Autowired
//   private UserDetailsService userDetailsService;


//   @Bean
//   @Primary
//   public PasswordEncoder customPasswordEncoder() {
//       return new BCryptPasswordEncoder();
//   }
//   @Bean
//   public AuthenticationProvider customAuthenticationProvider() {
//     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

//     provider.setUserDetailsService(userDetailsService);

//     provider.setPasswordEncoder(customPasswordEncoder());
//     return provider;
// }
  
    
}
