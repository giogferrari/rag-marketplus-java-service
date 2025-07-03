package com.ragnatales.marketplus.model;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private int card_id;
    private String card_name;

    public Card(int id, String name) {
        this.card_id = id;
        this.card_name = name;
    }

    public Card(Map<String, Object> map) {
        try {
            this.card_id = Integer.parseInt(map.get("id").toString());
        } catch (NumberFormatException e) {
            this.card_id = 0; // default value if parsing fails
        }
        this.card_name = map.get("name").toString();
    }
}
