package com.akhil.trading.controller;



import com.akhil.trading.domain.PaymentMethod;
import com.akhil.trading.model.PaymentOrder;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.response.PaymentResponse;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentController {



    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserContext userContext;



    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<Response> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable BigDecimal amount
            ) throws StripeException {



        PaymentResponse paymentResponse;

        PaymentOrder order= paymentService.createOrder(userContext.getId(), amount,paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZERPAY)){
            paymentResponse=paymentService.createRazorPayPayment(userContext,amount,
                    order.getId());
        }
        else{
            paymentResponse=paymentService.createStripePayment(userContext,amount, order.getId());
        }

        return  ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().data(paymentResponse).build());
    }


}

