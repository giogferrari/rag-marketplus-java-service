package com.ragnatales.marketplus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "opts")
@Data
@AllArgsConstructor
public class OptsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "opts")
    private Set<ItemEntity> items = new HashSet<>();
}
