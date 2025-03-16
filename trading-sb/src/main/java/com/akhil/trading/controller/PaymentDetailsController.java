package com.akhil.trading.controller;


import com.akhil.trading.model.PaymentDetails;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class PaymentDetailsController {

    @Autowired
    private UserContext userContext;

    @Autowired
    private PaymentDetailService paymentDetailsService;

    @PostMapping("/payment-details")
    public ResponseEntity<Response> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest)  {
        PaymentDetails paymentDetails=paymentDetailsService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                userContext.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().data(paymentDetails).build());
    }

    @GetMapping("/payment-details")
    public ResponseEntity<Response> getUsersPaymentDetails(){
        PaymentDetails paymentDetails=paymentDetailsService.getUserPaymentDetails(userContext.getId());
        return ResponseEntity.ok(Response.builder().data(paymentDetails).build());
    }
}
