package com.ragnatales.marketplus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.model.ItemResponse;
import com.ragnatales.marketplus.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class MarketService {
    private ItemRepository itemRepository;
    private final WebClient webClient;
    private static final String API_URL = "http://localhost:8000/api/items";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MarketService(WebClient.Builder webClientBuilder, ItemRepository itemRepository) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)) // 16MB
                .build();
        this.webClient = WebClient.builder()
                .baseUrl(API_URL)
                .exchangeStrategies(strategies)
                .build();
        this.itemRepository = itemRepository;
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

    public void saveIfNotExists(ItemEntity entity) {
        boolean exists = itemRepository.existsById(entity.getNameid());
        if (!exists) {
            itemRepository.save(entity);
        }
    }

    public void fetchAndSaveNewItems(String itemName) {
        getItems(itemName)
                .map(this::convertToEntity)
                .doOnNext(this::saveIfNotExists)
                .subscribe();
    }


    public ItemEntity convertToEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        entity.setNameid(item.getNameid());
        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setPrice(item.getPrice());
        entity.setShopName(item.getShop_name());
        entity.setMapname(item.getMapname());
        entity.setMapX(item.getMap_x());
        entity.setMapY(item.getMap_y());
        entity.setSlot(item.getSlot());

        try {
            String cardsJson = objectMapper.writeValueAsString(item.getCards());
            entity.setCardsJson(cardsJson);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter cards para JSON", e);
        }

        return entity;
    }
}

