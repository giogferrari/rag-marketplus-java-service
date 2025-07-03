package com.ragnatales.marketplus.controller;

import com.ragnatales.marketplus.DTO.CardDTO;
import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.repository.CardRepository;
import com.ragnatales.marketplus.service.MarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;
    private final CardRepository cardRepository;

    public MarketController(MarketService marketService, CardRepository cardRepository) {
        this.marketService = marketService;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/items")
    public Flux<Item> getItems(@RequestParam String item_name) {
        return marketService.getItems(item_name).doOnNext(item -> {
            ItemEntity entity = marketService.convertToEntity(item);
            marketService.saveIfNotExists(entity);
        });
    }


    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return cardRepository.findAll().stream()
                .map(card -> new CardDTO(card.getCardId(), card.getCardName()))
                .collect(Collectors.toList());
    }
}