package com.senla.main.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("SecretKey") 
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                
                List<GrantedAuthority> authorities = new ArrayList<>();

                
                if (claims.containsKey("roles")) {
                    List<?> roles = claims.get("roles", List.class);
                    authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role.toString())) 
                            .collect(Collectors.toList());
                }

                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("error", "Unauthorized: Invalid token");
                new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
                return; 
            }
        } else {
            
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Unauthorized: No token provided");
            new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
            return; 
        }

        chain.doFilter(request, response);
    }
}
