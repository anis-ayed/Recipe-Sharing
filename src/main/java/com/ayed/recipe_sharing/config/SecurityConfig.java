package com.ayed.recipe_sharing.config;

import com.ayed.recipe_sharing.filters.JwtAuthenticationFilter;
import com.ayed.recipe_sharing.services.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsServiceImpl;

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  private final CustomLogoutHandler logoutHandler;

  public SecurityConfig(
      UserDetailsServiceImpl userDetailsServiceImp,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      CustomLogoutHandler logoutHandler) {
    this.userDetailsServiceImpl = userDetailsServiceImp;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.logoutHandler = logoutHandler;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            req ->
                req.requestMatchers("/api/v1/login/**", "/api/v1/register/**")
                    .permitAll()
                    .requestMatchers("/", "/v3/api-docs/**", "/swagger-ui/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admin_only/**")
                    .hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated())
        .userDetailsService(userDetailsServiceImpl)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            e ->
                e.accessDeniedHandler(
                        (request, response, accessDeniedException) -> response.setStatus(403))
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .logout(
            l ->
                l.logoutUrl("/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(
                        (request, response, authentication) ->
                            SecurityContextHolder.clearContext()))
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
