package com.akhil.trading.controller;



import com.akhil.trading.domain.WalletTransactionType;
import com.akhil.trading.model.Order;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.model.Wallet;
import com.akhil.trading.model.WalletTransaction;
import com.akhil.trading.model.PaymentOrder;
import com.akhil.trading.response.PaymentResponse;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.OrderService;
import com.akhil.trading.service.PaymentService;
import com.akhil.trading.service.WalletService;
import com.akhil.trading.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserContext userContext;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletTransactionService walletTransactionService;


    @GetMapping("/wallet")
    public ResponseEntity<Response>  getUserWallet(){
        Wallet wallet=walletService.getUserWallet(userContext.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(wallet).build());
    }

    @PutMapping("/wallet/{walletId}/transfer")
    public ResponseEntity<Response> walletToWalletTransfer(@PathVariable Long walletId, @RequestBody WalletTransaction request){
        Wallet receiverWallet=walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransfer(userContext.getId(), receiverWallet,request.getAmount());
        walletTransactionService.createTransaction(
                wallet,
                WalletTransactionType.WALLET_TRANSFER,receiverWallet.getId().toString(),
                request.getPurpose(),
                request.getAmount()
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(wallet).build());
    }

    @PutMapping("/wallet/order/{orderId}/pay")
    public ResponseEntity<Response> payOrderPayment(@PathVariable Long orderId){
        Order order=orderService.getOrderById(orderId);
        Wallet wallet=walletService.payOrderPayment(order, userContext.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(wallet).build());

    }

    @PutMapping("/wallet/deposit")
    public ResponseEntity<Response> addMoneyToWallet(
            @RequestParam(name="order_id") Long orderId,
            @RequestParam(name="payment_id")String paymentId
    ) throws Exception {
        Wallet wallet = walletService.getUserWallet(userContext.getId());
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status=paymentService.ProceedPaymentOrder(order,paymentId);
        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposit success");

        if(status){
            wallet=walletService.addBalance(wallet, order.getAmount());
        }
        return ResponseEntity.ok(Response.builder().data(wallet).build());

    }
}
