package com.ragnatales.marketplus.controller;

import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import com.ragnatales.marketplus.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository repository;
    @GetMapping
    public List getAll() {
        List<ItemEntity> itemList = repository.findAll();
        return itemList;
    }
}
