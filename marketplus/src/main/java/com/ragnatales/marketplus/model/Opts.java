package com.ragnatales.marketplus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Opts {
    private int id;
    private String name;

    public Opts(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Opts(Map<String, Object> map) {
        try {
            this.id = Integer.parseInt(map.get("id").toString());
        } catch (NumberFormatException e) {
            this.id = 0; // default value if parsing fails
        }
        this.name = map.get("name").toString();
    }
}
