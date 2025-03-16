package com.akhil.trading.controller;

import com.akhil.trading.config.JwtConstant;
import com.akhil.trading.domain.VerificationType;
import com.akhil.trading.exception.InvalidOtpException;
import com.akhil.trading.model.ForgotPasswordToken;
import com.akhil.trading.model.User;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.model.VerificationCode;
import com.akhil.trading.request.ForgotPasswordTokenRequest;
import com.akhil.trading.request.ResetPasswordRequest;
import com.akhil.trading.response.RegisterResponse;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.ForgotPasswordService;
import com.akhil.trading.service.UserService;
import com.akhil.trading.service.VerificationCodeService;
import com.akhil.trading.service.impl.EmailServiceImpl;
import com.akhil.trading.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private UserContext userContext;

    @GetMapping("/api/users/profile")
    public ResponseEntity<Response> getUserProfile(@RequestHeader(JwtConstant.JWT_HEADER) String jwt){
        User user=userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(Response.builder().data(user).build());
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<Response> sendVerificationOtp(@PathVariable VerificationType verificationType) throws MessagingException {
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(userContext.getId());
        if(verificationCode==null){
            verificationCode=verificationCodeService.sendVerificationCode(userContext.getId(),verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(userContext.getEmail(),verificationCode.getOtp());
        }
        return ResponseEntity.ok(Response.builder().data("verification otp send successfully").build());

    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<Response> enableTwoFactorAuthentication(@PathVariable String otp){
        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(userContext.getId());
        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();
        boolean isVerified=verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo, userContext.getId());
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return ResponseEntity.ok(Response.builder().data(updatedUser).build());
        }
        throw new InvalidOtpException("invalid otp");
    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<Response> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws MessagingException {

        User user=userService.findUserByEmail(forgotPasswordTokenRequest.getSendTo());
        String otp= OtpUtils.generateOtp();
        String id= UUID.randomUUID().toString();
        ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId());
        if(token==null){
            token=forgotPasswordService.createToken(user,id,otp,forgotPasswordTokenRequest.getVerificationType(),forgotPasswordTokenRequest.getSendTo());
        }
        if (forgotPasswordTokenRequest.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
        }
        RegisterResponse response=RegisterResponse.builder().
                session(token.getId())
                .message("Password reset otp send successfully")
        .build();
        return ResponseEntity.ok(Response.builder().data(response).build());

    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<Response> resetPassword(
            @RequestParam String id, @RequestBody ResetPasswordRequest request){
        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findById(id);
        boolean isVerified=forgotPasswordToken.getOtp().equals(request.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUserId(),request.getPassword());
            forgotPasswordService.deleteToken(forgotPasswordToken);
            return ResponseEntity.ok(Response.builder().data("password update successfully").build());
        }
        throw new InvalidOtpException("invalid otp");
    }


}
