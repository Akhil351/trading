package com.akhil.trading.service.impl;

import com.akhil.trading.domain.OrderType;
import com.akhil.trading.exception.InSufficientBalance;
import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.Order;
import com.akhil.trading.model.User;
import com.akhil.trading.model.Wallet;
import com.akhil.trading.repo.WalletRepo;
import com.akhil.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepo walletRepo;
    private Wallet generateWallet(Long userId){
        Wallet wallet=new Wallet();
        wallet.setUserId(userId);
        return walletRepo.save(wallet);
    }
    @Override
    public Wallet getUserWallet(Long userId) {
        Wallet wallet=walletRepo.findByUserId(userId);
        if (wallet!=null){
           return wallet;
        }
        return generateWallet(userId);
    }

    @Override
    public Wallet addBalance(Wallet wallet, BigDecimal money) {
        wallet.setBalance(money.add(wallet.getBalance()));
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) {
        return walletRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("wallet not found"));
    }

    @Override
    public Wallet walletToWalletTransfer(Long senderId, Wallet recieverWallet, BigDecimal amount) {
        Wallet senderWallet=getUserWallet(senderId);
        if(senderWallet.getBalance().compareTo(amount)<0){
            throw new InSufficientBalance("Insufficient balance...");
        }
        recieverWallet.setBalance(recieverWallet.getBalance().add(amount));
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        walletRepo.save(recieverWallet);
        return walletRepo.save(senderWallet);
    }

    @Override
    public Wallet payOrderPayment(Order order, Long userId) {
        Wallet wallet=getUserWallet(userId);
        BigDecimal newBalance;
        if(order.getOrderType().equals(OrderType.BUY)){
            newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new InSufficientBalance("InSufficient funds for this transaction");
            }
        } else{
            newBalance = wallet.getBalance().add(order.getPrice());
        }
        wallet.setBalance(newBalance);
        walletRepo.save(wallet);

        return wallet;
    }
}
