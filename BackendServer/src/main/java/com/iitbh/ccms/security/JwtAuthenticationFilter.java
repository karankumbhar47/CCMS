package com.iitbh.ccms.security;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.model_db.UserDetailsDB.RoleAuthority;
import com.iitbh.ccms.service.UsersService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        // logger.info(" Header : {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);

            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username from token!!");
            } catch (ExpiredJwtException e) {
                logger.info("Give jwt token is expired!!");
            } catch (MalformedJwtException e) {
                logger.info("Some changes have been made to token!!");
            } catch (SignatureException e) {
                logger.info("Invalid signature");
            } catch (UnsupportedJwtException e) {
                logger.info("Invalid JWT token format");
            }

        } else {
            logger.info("invalid Header Value");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Optional<UserDetailsDB> optionalUser = usersService.singleUser(username);
            UserDetailsDB user = optionalUser.get();

            Boolean validToken = this.jwtHelper.validateToken(token, user);

            if (validToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                // for(RoleAuthority authority: user.getAuthorities()){
                //     System.out.println("JwtAuthenticationFilter.doFilterInternal()" + authority.getAuthority());
                // }

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Validation fails");
            }
        }
        System.out.println("JwtAuthenticationFilter.doFilterInternal(): Calling do filter");
        filterChain.doFilter(request, response);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
