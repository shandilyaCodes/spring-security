package com.shandilya.secured.kaushik.jwtAuth.code.filters;

import com.shandilya.secured.kaushik.jwtAuth.code.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (null != authHeader && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}