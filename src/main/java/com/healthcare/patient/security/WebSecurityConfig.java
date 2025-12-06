package com.healthcare.patient.security;

import com.healthcare.patient.security.jwt.AuthEntryPointJwt;
import com.healthcare.patient.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final CommonUserDetailsService commonUserDetailsService;
  private final AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(commonUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthTokenFilter authTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/healthcare/patient/signin",
                        "/healthcare/patient/signup",
                        "/healthcare/admin/signin",
                        "/healthcare/admin/signup",
                        "/healthcare/providers/login",
                        "/healthcare/providers/signup")
                    .permitAll()
                    .requestMatchers("/healthcare/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/healthcare/patient/**")
                    .hasRole("PATIENT")
                    .requestMatchers("/healthcare/providers/**")
                    .hasRole("Provider")
                    .anyRequest()
                    .authenticated());

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
