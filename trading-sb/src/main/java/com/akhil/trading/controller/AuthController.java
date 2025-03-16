package com.akhil.trading.controller;




import com.akhil.trading.exception.InvalidOtpException;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.TwoFactorOTP;
import com.akhil.trading.model.User;
import com.akhil.trading.repo.UserRepo;
import com.akhil.trading.request.LoginRequest;
import com.akhil.trading.request.RegisterRequest;
import com.akhil.trading.response.RegisterResponse;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.AuthService;

import com.akhil.trading.service.TwoFactorOtpService;
import com.akhil.trading.service.impl.EmailServiceImpl;
import com.akhil.trading.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class  AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailServiceImpl emailService;


    @PostMapping("/signup")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request){
        String jwt=authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().data(RegisterResponse.builder().jwt(jwt).build()).build());
    }

    @PostMapping("/signin")
    public ResponseEntity<Response> Login(@RequestBody LoginRequest request) throws MessagingException {
        String jwt=authService.login(request);
        User user=userRepo.findByEmail(request.getEmail()).orElseThrow(()->new  ResourceNotFoundException("user not found"));
        if (user.getTwoFactorAuth().isEnabled()){
            RegisterResponse response=RegisterResponse.builder()
                    .message("Two Factor auth is Enabled")
                    .twoFactorAuthEnabled(true)
                    .build();
            String otp= OtpUtils.generateOtp();
            Long userId=user.getId();
            TwoFactorOTP oldTwoFactorOtp=twoFactorOtpService.findByUser(userId);
            if(oldTwoFactorOtp!=null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOTP newTwoFactorOtp=twoFactorOtpService.createTwoFactorOtp(userId,otp, jwt);
            emailService.sendVerificationOtpEmail(user.getEmail(),otp);
            response.setSession(newTwoFactorOtp.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(response).build());


        }
        return ResponseEntity.ok(Response.builder().data(RegisterResponse.builder().message("login success").jwt(jwt).build()).build());
    }


    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<Response> verifySigningOtp(@PathVariable String otp,@RequestParam String id){
        TwoFactorOTP twoFactorOTP=twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)){
          RegisterResponse response=RegisterResponse.builder()
                  .message("Two factor authentication verified")
                  .twoFactorAuthEnabled(true)
                  .jwt(twoFactorOTP.getJwt())
                  .build();
          return ResponseEntity.ok(Response.builder().data(response).build());
        }
        throw new InvalidOtpException("invalid otp");

    }




}
