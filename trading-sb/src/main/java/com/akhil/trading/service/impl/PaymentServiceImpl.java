package com.akhil.trading.service.impl;

import com.akhil.trading.domain.PaymentMethod;
import com.akhil.trading.domain.PaymentOrderStatus;
import com.akhil.trading.exception.PaymentProcessingException;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.PaymentOrder;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.repo.PaymentOrderRepo;
import com.akhil.trading.response.PaymentResponse;
import com.akhil.trading.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentOrderRepo paymentOrderRepo;
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;
    @Override
    public PaymentOrder createOrder(Long userId, BigDecimal amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder=new PaymentOrder();
        paymentOrder.setUserId(userId);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING );
        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        return paymentOrderRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("payment order not found"));
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if (paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)) {
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZERPAY)) {
                RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
                Payment payment = razorpayClient.payments.fetch(paymentId);

                BigDecimal amount = new BigDecimal(payment.get("amount").toString());
                String status = payment.get("status").toString();

                if ("captured".equals(status)) {
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepo.save(paymentOrder);
                    return true;
                }
                paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepo.save(paymentOrder);
                return false;
            }
            paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorPayPayment(UserContext userContext, BigDecimal amount, Long orderId) {
        BigDecimal amountInPaise = amount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP);

        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amountInPaise.toBigIntegerExact());
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("description", "Payment for Order #" + orderId);
            paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet/" + orderId);
            paymentLinkRequest.put("callback_method", "get"); // ✅ Changed to "get"

            JSONObject customer = new JSONObject();
            customer.put("name", userContext.getFullName());
            customer.put("email", userContext.getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("sms", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("reminder_enable", true);

            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);
            return new PaymentResponse(paymentLink.get("short_url"));

        } catch (RazorpayException e) {
            log.error("Error creating Razorpay payment link for order {}: {}", orderId, e.getMessage());
            throw new PaymentProcessingException("Failed to create Razorpay payment link. Please try again later.", e);
        }
    }


    @Override
    public PaymentResponse createStripePayment(UserContext userContext, BigDecimal amount, Long orderId) throws StripeException {
            // Set the Stripe secret key
            Stripe.apiKey = stripeSecretKey;

            // Convert amount to cents (Stripe expects the smallest currency unit)
            Long amountInCents = amount.multiply(new BigDecimal(100)).longValue();

            // Create Stripe session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId) // Redirect on success
                    .setCancelUrl("http://localhost:5173/payment/cancel") // Redirect on cancel
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd") // Change to "inr" if needed
                                                    .setUnitAmount(amountInCents) // Convert to cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Top up wallet") // Payment description
                                                                    .build()
                                                    ).build()
                                    ).build()
                    ).build();

            // Create Stripe session
            Session session = Session.create(params);

            // Prepare response
            PaymentResponse res = new PaymentResponse();
            res.setPayment_url(session.getUrl()); // Payment link URL

            return res;
    }

}
