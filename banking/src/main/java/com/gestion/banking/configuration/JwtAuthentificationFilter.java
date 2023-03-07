package com.gestion.banking.configuration;

import com.gestion.banking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter  extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private static final String AUTHORIZATION = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader= request.getHeader(AUTHORIZATION);
        String userEmail = null;
        String jwt= null;
        //SecurityContextHolder.getContext().setAuthentication(null);
        if (authHeader ==null || !authHeader.startsWith(BEARER)){
            filterChain.doFilter(request,response);
            return;
        }
        jwt =authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwt);
        if (userEmail !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails userDetails= userRepository.findByEmail(userEmail)
                    .orElseThrow(()-> new EntityNotFoundException("User no found while validation JWT"));
            if (jwtUtils.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
