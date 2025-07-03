package com.ragnatales.marketplus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ItemResponse {
    private List<Item> items;

    @JsonProperty("total_items")
    private int totalItems;

    @JsonProperty("pages_processed")
    private int pagesProcessed;

    @JsonCreator
    public ItemResponse( @JsonProperty("items") List<Item> items,
                         @JsonProperty("total_items") int totalItems,
                         @JsonProperty("pages_processed") int pagesProcessed) {
        this.items = items;
        this.totalItems = totalItems;
        this.pagesProcessed = pagesProcessed;
    }
}