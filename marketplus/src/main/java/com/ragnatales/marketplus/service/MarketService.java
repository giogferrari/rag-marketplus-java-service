package com.ragnatales.marketplus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragnatales.marketplus.entity.CardEntity;
import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.entity.OptsEntity;
import com.ragnatales.marketplus.model.Card;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.model.ItemResponse;
import com.ragnatales.marketplus.repository.CardRepository;
import com.ragnatales.marketplus.repository.ItemRepository;
import com.ragnatales.marketplus.repository.OptsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketService {
    private ItemRepository itemRepository;
    private CardRepository cardRepository;
    private OptsRepository optsRepository;
    private final WebClient webClient;
    private static final String API_URL = "http://localhost:8000/api/items";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MarketService(WebClient.Builder webClientBuilder, ItemRepository itemRepository, CardRepository cardRepository, OptsRepository optsRepository) {
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
        this.cardRepository = cardRepository;
        this.optsRepository = optsRepository;
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

    @Transactional
    public void saveIfNotExists(ItemEntity item) {

        for (CardEntity cardEntity : item.getCard()) {
            cardRepository.save(cardEntity);
        }

        for (OptsEntity opt : item.getOpts()) {
            optsRepository.save(opt);
        }

        if (!itemRepository.existsById(item.getId())) {
            itemRepository.save(item);
        }
    }


    public void fetchAndSaveNewItems(String itemName) {
        getItems(itemName)
                .map(this::convertToEntity)
                .doOnNext(this::saveIfNotExists)
                .subscribe();
    }

    private CardEntity convertToCardEntity(Card card) {
        CardEntity entity = new CardEntity();
        entity.setCardId((long) card.getCard_id()); // ou outro campo chave
        entity.setCardName(card.getCard_name() != null ? card.getCard_name() : "No card name found");
        return entity;
    }

    private OptsEntity convertToOptsEntity(String opt) {
        OptsEntity entity = new OptsEntity();
        entity.setName(opt); // ajuste conforme seus campos
        return entity;
    }


    public ItemEntity convertToEntity(Item item) {
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId());
        entity.setNameid(item.getNameid());
        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setPrice(item.getPrice());
        entity.setShopName(item.getShop_name());
        entity.setMapname(item.getMapname());
        entity.setMapX(item.getMap_x());
        entity.setMapY(item.getMap_y());
        entity.setSlot(item.getSlot());

        if (item.getCards() != null) {
            Set<CardEntity> cardEntities = item.getCards().stream()
                    .map(this::convertToCardEntity)
                    .collect(Collectors.toSet());
            entity.setCard(cardEntities);
        } else {
            entity.setCard(new HashSet<>());
        }

        Set<OptsEntity> persistedOpts = new HashSet<>();
        for (String opt : item.getOpts()) {
            OptsEntity optEntity = new OptsEntity();
            optEntity.setName(opt);

            // Busca por Opt existente no banco
            Optional<OptsEntity> existing = optsRepository.findByName(opt);

            persistedOpts.add(existing.orElseGet(() -> optsRepository.save(optEntity)));
        }
        entity.setOpts(persistedOpts);


        return entity;
    }
}

