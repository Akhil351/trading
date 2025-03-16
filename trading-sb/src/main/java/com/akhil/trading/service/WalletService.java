package com.akhil.trading.service;

import com.akhil.trading.model.Order;

import com.akhil.trading.model.Wallet;

import java.math.BigDecimal;

public interface WalletService {
  Wallet getUserWallet(Long userId);
  Wallet addBalance(Wallet wallet, BigDecimal money);
  Wallet findWalletById(Long id);
  Wallet walletToWalletTransfer(Long userId,Wallet recieverWallet,BigDecimal amount);
  Wallet payOrderPayment(Order order, Long userId);
}
