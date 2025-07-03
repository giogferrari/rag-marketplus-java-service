package com.ragnatales.marketplus.repository;

import com.ragnatales.marketplus.entity.OptsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptsRepository extends JpaRepository<OptsEntity, Long> {
    Optional<OptsEntity> findByName(String name);
}
