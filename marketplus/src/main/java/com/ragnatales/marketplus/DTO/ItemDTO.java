package com.ragnatales.marketplus.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long nameid;
    private String name;
    private String description;
    private Double price;
    private String shop_name;
    private String mapname;
    private Integer map_x;
    private Integer map_y;
    private Integer slot;
    private List<String> opts;
    private List<String> cards;
}
