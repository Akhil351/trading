package com.akhil.trading.service;

import com.akhil.trading.domain.PaymentMethod;
import com.akhil.trading.model.PaymentOrder;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentOrder createOrder(Long userId, BigDecimal amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id);
    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
    PaymentResponse createRazorPayPayment(UserContext userContext, BigDecimal amount,Long orderId);
    PaymentResponse createStripePayment(UserContext userContext,BigDecimal amount,Long orderId) throws StripeException;
}
