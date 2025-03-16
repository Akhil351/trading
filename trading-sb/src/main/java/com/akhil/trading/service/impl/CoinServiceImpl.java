package com.akhil.trading.service.impl;

import com.akhil.trading.exception.ResourceNotFoundException;
import com.akhil.trading.exception.WebClientException;
import com.akhil.trading.model.Coin;
import com.akhil.trading.repo.CoinRepo;
import com.akhil.trading.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinRepo coinRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebClient webClient;

    @Override
    public List<Coin> getCoinList(int page) {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+ page;

        try {
            String responseBody = webClient.get()
                    .uri(url)
                    //.header("x-cg-demo-api-key", API_KEY)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Blocking call, replace with async processing if needed

            return objectMapper.readValue(responseBody, new TypeReference<List<Coin>>() {});
        } catch (WebClientResponseException | JsonProcessingException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("Please wait, as you are using a free plan.");
        }
    }

    @Override
    public String getMarketChat(String coinId, int days) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com")
                //.defaultHeader("x-cg-demo-api-key", API_KEY)
                .build();

        try {
            return webClient.get()
                    .uri("/api/v3/coins/{coinId}/market_chart?vs_currency=usd&days={days}", coinId, days)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("You are using a free plan");
        }
    }

    @Override
    public String getCoinDetails(String coinId) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com")
               // .defaultHeader("x-cg-demo-api-key", API_KEY)
                .build();

        try {
            String responseBody = webClient.get()
                    .uri("/api/v3/coins/{coinId}", coinId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            Coin coin = new Coin();

            coin.setId(jsonNode.get("id").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.get("market_data");
            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(jsonNode.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
            coin.setCirculatingSupply(marketData.get("circulating_supply").asLong());
            coin.setTotalSupply(marketData.get("total_supply").asLong());

            coinRepo.save(coin);
            return responseBody;
        } catch (WebClientResponseException | JsonProcessingException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("You are using a free plan");
        }
    }

    @Override
    public Coin findById(String coinId) {

        return coinRepo.findById(coinId)
                .orElseThrow(()->new ResourceNotFoundException("Coin not found"));
    }

    @Override
    public String searchCoin(String keyword) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com")
               // .defaultHeader("x-cg-demo-api-key", API_KEY)
                .build();

        try {
            return webClient.get()
                    .uri("/api/v3/search?query={keyword}", keyword)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("You are using a free plan");
        }
    }



    @Override
    public String getTop50CoinByMarketCapRank() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com")
                //.defaultHeader("x-cg-demo-api-key", API_KEY)
                .build();

        try {
            return webClient.get()
                    .uri("/api/v3/coins/markets?vs_currency=usd&page=1&per_page=50")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("You are using a free plan");
        }
    }

    @Override
    public String getTradingCoins() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.coingecko.com")
              //  .defaultHeader("x-cg-demo-api-key", API_KEY)
                .build();

        try {
            return webClient.get()
                    .uri("/api/v3/search/trending")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Error: " + e.getMessage());
            throw new WebClientException("You are using a free plan");
        }
    }
}
