package com.iitbh.ccms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.iitbh.ccms.security.JWTAuthenticationEntryPoint;
import com.iitbh.ccms.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("Security config called");

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/announcements").hasAnyAuthority("User", "Admin")
                        .requestMatchers("/announcements/*").hasAnyAuthority("Admin")
                        .requestMatchers("/complain_overview").hasAnyAuthority("User", "Admin")
                        .requestMatchers("/submit_complain").hasAnyAuthority("User")
                        .requestMatchers("/userDetails").hasAnyAuthority("User", "Admin")
                        .requestMatchers("/userDetailsUpdate").hasAuthority("Admin")
                        .requestMatchers("/getComplaintInfo/*").hasAnyAuthority("User", "Admin")
                        .requestMatchers("/createUser").hasAuthority("Admin")
                        .requestMatchers("/deleteUser").hasAuthority("Admin")
                        .requestMatchers("/upload/image").hasAuthority("User")
                        .requestMatchers("/download/image/*").hasAnyAuthority("Admin", "User")
                        .requestMatchers("/getUserInfo").hasAnyAuthority("Admin", "User")
                        .requestMatchers("/getLocation").hasAnyAuthority("Admin")
                        .requestMatchers("/updateLocation").hasAnyAuthority("Admin")
                        .anyRequest().authenticated()
                        )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // try with add filter after

        return http.build();
    }
}
