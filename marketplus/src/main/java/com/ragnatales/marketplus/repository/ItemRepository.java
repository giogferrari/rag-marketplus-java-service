package com.ragnatales.marketplus.repository;

import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    boolean existsByName(String name);
}
