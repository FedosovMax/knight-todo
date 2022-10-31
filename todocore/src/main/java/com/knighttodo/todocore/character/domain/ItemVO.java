package com.knighttodo.todocore.character.domain;

import com.knighttodo.todocore.character.service.privatedb.representation.enums.Rarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemVO {

        private String id;

        private String name;

        private String description;

        private Integer requiredLevel;

        private Integer requiredStrength;

        private Integer requiredAgility;

        private Integer requiredIntelligence;

        private Rarity rarity;

        private List<BonusVO> bonuses = new ArrayList<>();
}
