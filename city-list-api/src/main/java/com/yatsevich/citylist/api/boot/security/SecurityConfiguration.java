package com.yatsevich.citylist.api.boot.security;

import static com.yatsevich.citylist.api.boot.security.UserAuthority.FULL_AUTHORITY;
import static com.yatsevich.citylist.api.boot.security.UserAuthority.READ_ONLY_AUTHORITY;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableConfigurationProperties(BasicSecurityConfigurationProperties.class)
public class SecurityConfiguration {

  private final BasicSecurityConfigurationProperties properties;

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    UserDetails readOnlyUser = User.withUsername(properties.getReadOnlyUser().getUsername())
        .password(passwordEncoder().encode(properties.getReadOnlyUser().getPassword())).authorities(READ_ONLY_AUTHORITY)
        .build();

    UserDetails fullAccessUser = User.withUsername(properties.getFullAccessUser().getUsername())
        .password(passwordEncoder().encode(properties.getFullAccessUser().getPassword()))
        .authorities(READ_ONLY_AUTHORITY, FULL_AUTHORITY).build();

    manager.createUser(readOnlyUser);
    manager.createUser(fullAccessUser);
    return manager;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests().requestMatchers("/public/**").permitAll()
        .requestMatchers("/login", "/", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**")
        .permitAll().anyRequest().authenticated().and().httpBasic();

    http.csrf().disable();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
