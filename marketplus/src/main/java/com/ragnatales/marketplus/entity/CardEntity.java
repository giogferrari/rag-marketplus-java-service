package com.ragnatales.marketplus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

    @Id
    private Long cardId;

    @Column(name = "card_name", nullable = false)
    private String cardName;

    @ManyToMany(mappedBy = "card")
    private Set<ItemEntity> items = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(cardId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEntity)) return false;
        return cardId != null && cardId.equals(((ItemEntity) o).getId());

    }

}
