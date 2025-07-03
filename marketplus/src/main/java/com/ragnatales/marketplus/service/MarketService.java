package com.ragnatales.marketplus.service;

import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.model.ItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class MarketService {
    private final WebClient webClient;
    private static final String API_URL = "http://localhost:8000/api/items";

    public MarketService(WebClient.Builder webClientBuilder) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)) // 16MB
                .build();
        this.webClient = WebClient.builder()
                .baseUrl(API_URL)
                .exchangeStrategies(strategies)
                .build();
    }

    public Flux<Item> getItems(String itemName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("item_name", itemName)
                        .queryParam("max_pages", 99)
                        .build())
                .retrieve()
                .bodyToMono(ItemResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.getItems()));
    }
}

