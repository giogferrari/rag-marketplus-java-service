package com.ragnatales.marketplus.controller;

import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.service.MarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping
    public Flux<Item> getItems(@RequestParam String item_name) {
        return marketService.getItems(item_name).doOnNext(item -> {
            ItemEntity entity = marketService.convertToEntity(item);
            marketService.saveIfNotExists(entity);
        });
    }
}