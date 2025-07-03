package com.ragnatales.marketplus.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@Data
public class ItemEntity {

    @Id
    private Long nameid;

    private String name;

    private String description;

    private Double price;

    private String shopName;

    private String mapname;

    private Integer mapX;

    private Integer mapY;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cards_json", columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<String> cardsJson;

    private Integer slot;

    @ManyToMany
    @JoinTable(
            name = "item_opts",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "opt_id")
    )
    private Set<OptsEntity> opts = new HashSet<>();

}
