package com.akhil.trading.controller;


import com.akhil.trading.model.Coin;
import com.akhil.trading.model.UserContext;
import com.akhil.trading.model.WatchList;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.CoinService;
import com.akhil.trading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private WatchListService watchlistService;

    @Autowired
    private UserContext userContext;

    @Autowired
    private CoinService coinService;


    @GetMapping("/user")
    public ResponseEntity<Response> getUserWatchlist(){
        WatchList watchlist = watchlistService.findUserWatchList(userContext.getId());
        return ResponseEntity.ok(Response.builder().data(watchlist).build());

    }

    @PostMapping("/create")
    public ResponseEntity<Response> createWatchlist() {;
        WatchList createdWatchlist = watchlistService.createWatchList(userContext.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().data(createdWatchlist).build());
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Response> getWatchlistById(
            @PathVariable Long watchlistId)  {

        WatchList watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(Response.builder().data(watchlist).build());

    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Response> addItemToWatchlist(
            @PathVariable String coinId)  {

        Coin coin=coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchList(coin, userContext.getId());
        return ResponseEntity.ok(Response.builder().data(addedCoin).build());

    }
}

