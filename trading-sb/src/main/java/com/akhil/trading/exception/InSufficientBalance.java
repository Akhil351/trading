package com.akhil.trading.exception;

public class InSufficientBalance extends RuntimeException {
    public InSufficientBalance(String message) {
        super(message);
    }
}
