package com.ragnatales.marketplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Item {
    private long id;
    private Long nameid;
    private String name;
    private String description;
    private Double price;
    private String shop_name;
    private String mapname;
    private Integer map_x;
    private Integer map_y;
    private List<String> opts;
    private List<Card> cards;
    private Integer slot;


    @JsonProperty("data")
    private void unpackData(Map<String, Object> data) {
        Object desc = data.get("description");
        this.description = desc != null ? desc.toString() : "No description";

    }
}


