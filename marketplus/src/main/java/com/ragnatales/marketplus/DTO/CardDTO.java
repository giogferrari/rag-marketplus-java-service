package com.ragnatales.marketplus.DTO;

import com.ragnatales.marketplus.entity.ItemEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
public class CardDTO
{

    private Long cardId;
    private String cardName;

}
