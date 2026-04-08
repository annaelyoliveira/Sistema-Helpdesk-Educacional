package com.helpdesk.config;

import com.helpdesk.Security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/tecnicos").hasAnyAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.POST, "/chamados").hasAuthority("ROLE_USUARIO")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios").hasAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/chamados/**").hasAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/chamados/**").hasAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.DELETE, "/chamados/**").hasAuthority("ROLE_TECNICO")
                        .requestMatchers("/tecnicos/**").hasAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority("ROLE_TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasAuthority("ROLE_USUARIO")
                        .anyRequest().authenticated()).httpBasic(httpBasic -> {});
        return http.build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
