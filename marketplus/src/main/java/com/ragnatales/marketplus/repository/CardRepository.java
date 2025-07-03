package com.ragnatales.marketplus.repository;

import com.ragnatales.marketplus.entity.CardEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    boolean existsByCardName(String card_name);
}

