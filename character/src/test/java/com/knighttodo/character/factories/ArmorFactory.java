package com.knighttodo.character.factories;

import com.knighttodo.character.gateway.privatedb.representation.Armor;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.character.gateway.privatedb.representation.enums.ArmorType;
import com.knighttodo.character.gateway.privatedb.representation.enums.Rarity;
import com.knighttodo.character.rest.request.ArmorRequestDto;

import java.util.Collections;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArmorFactory {

    private final Integer ARMOR_DEFENCE = 1;
    private final ArmorType ARMOR_TYPE = ArmorType.SHOES;
    private final String ARMOR_NAME = "Ogre Shoes";
    private final String ARMOR_DESCRIPTION = "Magic shoes worn by ancient ogres";
    private final Rarity ARMOR_RARITY = Rarity.LEGEND;
    private final Integer ARMOR_REQUIRED_AGILITY = 2;
    private final Integer ARMOR_REQUIRED_INTELLIGENCE = 3;
    private final Integer ARMOR_REQUIRED_LEVEL = 4;
    private final Integer ARMOR_REQUIRED_STRENGTH = 5;
    private final String INVALID_ARMOR_TYPE = "SUPER ARMOR";
    private final String INVALID_ARMOR_RARITY = "MYSTIC ORANGE";
    private final Integer UPDATED_ARMOR_DEFENCE = 91;
    private final ArmorType UPDATED_ARMOR_TYPE = ArmorType.GLOVES;
    private final String UPDATED_ARMOR_NAME = "Ogre Gloves";
    private final String UPDATED_ARMOR_DESCRIPTION = "Magic gloves worn by ancient ogres";
    private final Rarity UPDATED_ARMOR_RARITY = Rarity.MYTHICAL;
    private final Integer UPDATED_ARMOR_REQUIRED_AGILITY = 92;
    private final Integer UPDATED_ARMOR_REQUIRED_INTELLIGENCE = 93;
    private final Integer UPDATED_ARMOR_REQUIRED_LEVEL = 94;
    private final Integer UPDATED_ARMOR_REQUIRED_STRENGTH = 95;

    public ArmorRequestDto createArmorRequestDto(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutDefence(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutArmorType(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithInvalidArmorType(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(INVALID_ARMOR_TYPE)
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutName(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithNameConsistingOfSpaces(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name("    ")
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutDescription(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithDescriptionConsistingOfSpaces(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description("    ")
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutRequiredLevel(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutRequiredAgility(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutRequiredIntelligence(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutRequiredStrength(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutRarity(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithInvalidRarity(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(INVALID_ARMOR_RARITY)
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto createArmorRequestDtoWithoutBonusIds() {
        return ArmorRequestDto.builder()
            .defence(ARMOR_DEFENCE)
            .armorType(ARMOR_TYPE.name())
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY.name())
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public Armor armorInstance(List<Bonus> bonuses) {
        return Armor.builder()
            .defence(ARMOR_DEFENCE)
            .bonuses(bonuses)
            .armorType(ARMOR_TYPE)
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY)
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public Armor armorInstance() {
        return Armor.builder()
            .defence(ARMOR_DEFENCE)
            .bonuses(Collections.emptyList())
            .armorType(ARMOR_TYPE)
            .name(ARMOR_NAME)
            .description(ARMOR_DESCRIPTION)
            .rarity(ARMOR_RARITY)
            .requiredAgility(ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(ARMOR_REQUIRED_LEVEL)
            .requiredStrength(ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDto(List<String> bonusIds) {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(bonusIds)
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutDefence() {
        return ArmorRequestDto.builder()
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutRarity() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithInvalidRarity() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(INVALID_ARMOR_RARITY)
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutArmorType() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithInvalidArmorType() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(INVALID_ARMOR_TYPE)
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutName() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithNameConsistingOfSpaces() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name("    ")
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutDescription() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithDescriptionConsistingOfSpaces() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description("    ")
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutRequiredAgility() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutRequiredIntelligence() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutRequiredLevel() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutRequiredStrength() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .bonusIds(Collections.emptyList())
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .build();
    }

    public ArmorRequestDto updateArmorRequestDtoWithoutBonusIds() {
        return ArmorRequestDto.builder()
            .defence(UPDATED_ARMOR_DEFENCE)
            .armorType(UPDATED_ARMOR_TYPE.name())
            .name(UPDATED_ARMOR_NAME)
            .description(UPDATED_ARMOR_DESCRIPTION)
            .rarity(UPDATED_ARMOR_RARITY.name())
            .requiredAgility(UPDATED_ARMOR_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_ARMOR_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_ARMOR_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_ARMOR_REQUIRED_STRENGTH)
            .build();
    }
}
