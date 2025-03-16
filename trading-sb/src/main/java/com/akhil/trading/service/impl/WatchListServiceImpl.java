package com.akhil.trading.service.impl;

import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.model.Coin;
import com.akhil.trading.model.WatchList;
import com.akhil.trading.repo.WatchListRepo;
import com.akhil.trading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchListServiceImpl implements WatchListService {
    @Autowired
    private WatchListRepo watchListRepo;
    @Override
    public WatchList findUserWatchList(Long userId) {
        return watchListRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("watch list not found"));
    }

    @Override
    public WatchList createWatchList(Long userId) {
        WatchList watchList=new WatchList();
        watchList.setUserId(userId);
        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findById(Long id) {
        return watchListRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("watch list not found"));
    }

    @Override
    public Coin addItemToWatchList(Coin coin, Long userId) {
        WatchList watchList=findUserWatchList(userId);
        if(watchList.getCoinsIds().contains(coin.getId())){
            watchList.getCoinsIds().remove(coin.getId());
        }
        else{
            watchList.getCoinsIds().add(coin.getId());
        }
        watchListRepo.save(watchList);
        return coin;
    }
}
