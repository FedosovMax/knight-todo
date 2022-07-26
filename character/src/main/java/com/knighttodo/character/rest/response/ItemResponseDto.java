package com.knighttodo.character.rest.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemResponseDto {

    private String id;

    private String name;

    private String description;

    private Integer requiredLevel;

    private Integer requiredStrength;

    private Integer requiredAgility;

    private Integer requiredIntelligence;

    private String rarity;

    private List<BonusResponseDto> bonuses = new ArrayList<>();
}
