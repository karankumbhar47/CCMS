package com.iitbh.ccms.security.security;

import com.iitbh.ccms.security.models.Myuser;
import com.iitbh.ccms.security.services.MyuserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private MyuserService myuserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        logger.info(" Header :  {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username from token!!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e) {
                logger.info("Give jwt token is expired!!");
                e.printStackTrace();
            }
            catch (MalformedJwtException e) {
                logger.info("Some changes have been made to token!!");
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("invalid Header Value");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

// start
            Optional<Myuser> user = myuserService.singleEmail(username);
            UserDetails userDetails = User.builder().username(user.get().getEmail())
                    .password(passwordEncoder().encode(user.get().getPassword()))
                    .roles("ADMIN").build();
// end

            Boolean validToken = this.jwtHelper.validateToken(token, userDetails);

            if (validToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation fails");
            }
        }
        filterChain.doFilter(request, response);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}