package com.ragnatales.marketplus.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ragnatales.marketplus.model.Card;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@Data
public class ItemEntity {

    @Id
    private Long id;

    private Long nameid;

    private String name;

    @Column(length = 2048)
    private String description;

    private Double price;

    private String shopName;

    private String mapname;

    private Integer mapX;

    private Integer mapY;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb not null default '[]'::jsonb", length = 1024)
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "item_cards",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private Set<CardEntity> card = new HashSet<>();

    private Integer slot;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "item_opts",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "opt_id")
    )
    private Set<OptsEntity> opts = new HashSet<>();

}
