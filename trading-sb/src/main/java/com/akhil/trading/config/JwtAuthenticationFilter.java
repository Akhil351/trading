package com.akhil.trading.config;


import com.akhil.trading.model.UserContext;
import com.akhil.trading.service.impl.UserDetailsImpl;
import com.akhil.trading.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserContext userContext;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt=jwtUtils.getJwtFromHeader(request);
            if (jwt!=null && jwtUtils.validateToken(jwt)){
                String userName=jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetailsImpl userDetails=(UserDetailsImpl) userDetailsService.loadUserByUsername(userName);
                setContext(userDetails);
                if (userDetails!=null){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);

    }

    private void setContext(UserDetailsImpl userDetails){
      userContext.setId(userDetails.getId());
      userContext.setFullName(userDetails.getFullName());
      userContext.setEmail(userDetails.getUsername());
      userContext.setAuthorities(userDetails.getAuthorities());
    }

}