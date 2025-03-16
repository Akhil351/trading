package com.akhil.trading.exception;

import com.razorpay.RazorpayException;

public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message, RazorpayException e) {
        super(message);
    }
}
