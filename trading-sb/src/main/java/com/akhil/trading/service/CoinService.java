package com.akhil.trading.service;

import com.akhil.trading.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);
    String getMarketChat(String coinId,int days);
    String getCoinDetails(String coinId);
    Coin findById(String coinId);
    String searchCoin(String keyword);

    String getTop50CoinByMarketCapRank();
    String getTradingCoins();
}
