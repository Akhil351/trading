package com.akhil.trading.service;

import com.akhil.trading.model.Coin;
import com.akhil.trading.model.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId);

    WatchList createWatchList(Long userId);

    WatchList findById(Long id);

    Coin addItemToWatchList(Coin coin,Long userId);
}
