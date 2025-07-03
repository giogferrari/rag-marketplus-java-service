package com.ragnatales.marketplus.mapper;

import com.ragnatales.marketplus.DTO.ItemDTO;
import com.ragnatales.marketplus.entity.ItemEntity;
import com.ragnatales.marketplus.entity.OptsEntity;
import com.ragnatales.marketplus.repository.OptsRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemEntity toEntity(ItemDTO dto, OptsRepository optRepository) {
        ItemEntity item = new ItemEntity();
        item.setNameid(dto.getNameid());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setShopName(dto.getShop_name());
        item.setMapname(dto.getMapname());
        item.setMapX(dto.getMap_x());
        item.setMapY(dto.getMap_y());
        item.setSlot(dto.getSlot());

        if (dto.getOpts() != null) {
            Set<OptsEntity> opts = dto.getOpts().stream()
                    .map(optName -> optRepository.findByName(optName)
                            .orElseGet(() -> new OptsEntity(null, optName)))
                    .collect(Collectors.toSet());
            item.setOpts(opts);
        }

        return item;
    }
}
