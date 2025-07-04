package com.ragnatales.marketplus.controller;

import com.ragnatales.marketplus.DTO.CardDTO;
import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.repository.CardRepository;
import com.ragnatales.marketplus.repository.OptsRepository;
import com.ragnatales.marketplus.service.MarketService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@RequestMapping("/api/market")
public class ItemController {

    private final MarketService marketService;
    private final CardRepository cardRepository;
    private final OptsRepository optsRepository;

    public ItemController(MarketService marketService, CardRepository cardRepository, OptsRepository optsRepository) {
        this.marketService = marketService;
        this.cardRepository = cardRepository;
        this.optsRepository = optsRepository;
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

    @GetMapping("/opts")
    public List<String> getUniqueOptsText() {
        return optsRepository.findAll().stream()
                .map(opts -> opts.getName()
                        // Replace all number groups with X
                        .replaceAll("\\d+", "X")
                        // Normalize +X% cases
                        .replaceAll("[+\\-]X%", "+X%")
                        // Normalize standalone +X cases
                        .replaceAll("[+\\-]X", "+X")
                        // Clean up any double spaces
                        .replaceAll("\\s+", " ")
                        .trim())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}