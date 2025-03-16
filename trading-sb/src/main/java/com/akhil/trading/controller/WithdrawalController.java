package com.akhil.trading.controller;





import com.akhil.trading.domain.WalletTransactionType;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.model.Wallet;
import com.akhil.trading.model.Withdrawal;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.UserService;
import com.akhil.trading.service.WalletService;
import com.akhil.trading.service.WalletTransactionService;
import com.akhil.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserContext userContext;

    @Autowired
    private WalletTransactionService walletTransactionService;



    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<Response> withdrawalRequest(
            @PathVariable BigDecimal amount) throws Exception {
        Wallet userWallet=walletService.getUserWallet(userContext.getId());

        Withdrawal withdrawal=withdrawalService.requestWithdrawal(amount,userContext.getId());
        walletService.addBalance(userWallet, withdrawal.getAmount().multiply(BigDecimal.valueOf(-1)));

        walletTransactionService.createTransaction(
                userWallet,
                WalletTransactionType.WITHDRAWAL,null,
                "bank account withdrawal",
                withdrawal.getAmount()
        );

        return ResponseEntity.ok(Response.builder().data(withdrawal).build());
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<Response> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept) throws Exception {

        Withdrawal withdrawal=withdrawalService.procedWithwithdrawal(id,accept);

        Wallet userWallet=walletService.getUserWallet(userContext.getId());
        if(!accept){
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }

        return ResponseEntity.ok(Response.builder().data(withdrawal).build());
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<Response> getWithdrawalHistory() throws Exception {
        List<Withdrawal> withdrawal=withdrawalService.getUsersWithdrawalHistory(userContext.getId());

        return ResponseEntity.ok(Response.builder().data(withdrawal).build());
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<Response> getAllWithdrawalRequest() throws Exception {

        List<Withdrawal> withdrawal=withdrawalService.getAllWithdrawalRequest();

        return ResponseEntity.ok(Response.builder().data(withdrawal).build());
    }
}

