package com.akhil.trading.service.impl;


 import com.akhil.trading.service.WatchListService;
import com.akhil.trading.utils.JwtUtils;
import com.akhil.trading.exception.DuplicateException;
import com.akhil.trading.model.User;
import com.akhil.trading.repo.UserRepo;
import com.akhil.trading.request.LoginRequest;
import com.akhil.trading.request.RegisterRequest;
import com.akhil.trading.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public String register(RegisterRequest request) {
        userRepo.findByEmail(request.getEmail()).ifPresent((u)->{
            throw new DuplicateException("email is already used with another account");
        });
        User newUser=new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullName());
        newUser.setMobile(request.getMobile());
        User savedUser=userRepo.save(newUser);
        watchListService.createWatchList(savedUser.getId());
        Authentication authentication=new UsernamePasswordAuthenticationToken(newUser.getEmail(),newUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(UserDetailsImpl.build(newUser));
    }

    @Override
    public String login(LoginRequest request) {
        String userName=request.getEmail();
        String password=request.getPassword();
        Authentication authentication=authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken((UserDetailsImpl)authentication.getPrincipal());
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }
}
