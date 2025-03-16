package com.akhil.trading.exception;

import com.akhil.trading.response.Response;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Response> duplicateException(DuplicateException exception){
        return ResponseEntity.status(ALREADY_REPORTED).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> duplicateException(BadCredentialsException exception){
        return ResponseEntity.status(FORBIDDEN).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Response> invalidOtpException(InvalidOtpException exception){
        return ResponseEntity.status(FORBIDDEN).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> resourceNotFoundException(ResourceNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(InSufficientBalance.class)
    public ResponseEntity<Response> inSufficientBalance(InSufficientBalance exception){
        return ResponseEntity.status(422).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Response> apiException(ApiException exception){
        return ResponseEntity.status(500).body(Response.builder().status("Failed").error(exception.getMessage()).build());
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<Response> handleWebClientException(WebClientException exception) {
        return ResponseEntity.status(SERVICE_UNAVAILABLE)
                .body(Response.builder()
                        .status("Failed")
                        .error("External API call failed: " + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<Response> handleRazorpayException(PaymentProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().status("Failed").error("Razorpay Error: " + ex.getMessage()).build());
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<Response> handleStripeException(StripeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Payment processing failed");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().status("Failed").error(errorResponse).build());
    }
}
