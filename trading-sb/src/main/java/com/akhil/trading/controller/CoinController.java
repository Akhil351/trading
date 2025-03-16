package com.akhil.trading.controller;

import com.akhil.trading.model.Coin;
import com.akhil.trading.response.Response;
import com.akhil.trading.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinService coinService;


    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Response> getCoinList(@RequestParam(required = false,name = "page") int page){
        List<Coin> coins=coinService.getCoinList(page);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(coins).build());
    }

    @GetMapping("/{coinId}/chart")
    public ResponseEntity<Response> getMarketChart(@PathVariable String coinId, @RequestParam("days") int days) throws JsonProcessingException {
        String res=coinService.getMarketChat(coinId,days);
        JsonNode jsonNode=objectMapper.readTree(res);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder().data(jsonNode).build());
    }

    @GetMapping("/search")
    ResponseEntity<Response> searchCoin(@RequestParam("q") String keyword) throws JsonProcessingException {
        String coin=coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(coin);
        return ResponseEntity.ok(Response.builder().data(jsonNode).build());

    }
    @GetMapping("/top50")
    ResponseEntity<Response> getTop50CoinByMarketCapRank() throws JsonProcessingException {
        String coin=coinService.getTop50CoinByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(coin);
        return ResponseEntity.ok(Response.builder().data(jsonNode).build());

    }

    @GetMapping("/trading")
    ResponseEntity<Response> getTreadingCoin() throws JsonProcessingException {
        String coin=coinService.getTradingCoins();
        JsonNode jsonNode = objectMapper.readTree(coin);
        return ResponseEntity.ok(Response.builder().data(jsonNode).build());

    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<Response> getCoinDetails(@PathVariable String coinId) throws JsonProcessingException {
        String coin=coinService.getCoinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coin);
        return ResponseEntity.ok(Response.builder().data(jsonNode).build());

    }

}
