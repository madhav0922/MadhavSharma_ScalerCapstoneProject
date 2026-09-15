package com.spendwise.common.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwt;
    private final CustomUserDetailsService users;

    public JwtAuthenticationFilter(JwtService j, CustomUserDetailsService u) {
        jwt = j;
        users = u;
    }

    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String h = req.getHeader("Authorization");
        if (h != null && h.startsWith("Bearer ")) {
            try {
                String token = h.substring(7), email = jwt.username(token);
                UserDetails u = users.loadUserByUsername(email);
                if (jwt.valid(token, u) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var a = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                    a.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(a);
                }
            } catch (Exception ignored) {
            }
        }
        chain.doFilter(req, res);
    }
}