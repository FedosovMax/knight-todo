package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Weapon;
import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.Rarity;
import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.WeaponType;
import com.knighttodo.todocore.character.rest.request.WeaponRequestDto;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class WeaponFactory {

    private final Integer WEAPON_DAMAGE = 1;
    private final WeaponType WEAPON_TYPE = WeaponType.AXE;
    private final String WEAPON_NAME = "Ogre Axe";
    private final String WEAPON_DESCRIPTION = "Deadly axe used by ancient ogres";
    private final Rarity WEAPON_RARITY = Rarity.LEGEND;
    private final Integer WEAPON_REQUIRED_AGILITY = 2;
    private final Integer WEAPON_REQUIRED_INTELLIGENCE = 3;
    private final Integer WEAPON_REQUIRED_LEVEL = 4;
    private final Integer WEAPON_REQUIRED_STRENGTH = 5;
    private final String INVALID_WEAPON_TYPE = "SUPER WEAPON";
    private final String INVALID_WEAPON_RARITY = "MYSTIC ORANGE";
    private final Integer UPDATED_WEAPON_DAMAGE = 91;
    private final WeaponType UPDATED_WEAPON_TYPE = WeaponType.KNIFE;
    private final String UPDATED_WEAPON_NAME = "Ogre Knife";
    private final String UPDATED_WEAPON_DESCRIPTION = "Deadly knife used by ancient ogres";
    private final Rarity UPDATED_WEAPON_RARITY = Rarity.MYTHICAL;
    private final Integer UPDATED_WEAPON_REQUIRED_AGILITY = 92;
    private final Integer UPDATED_WEAPON_REQUIRED_INTELLIGENCE = 93;
    private final Integer UPDATED_WEAPON_REQUIRED_LEVEL = 94;
    private final Integer UPDATED_WEAPON_REQUIRED_STRENGTH = 95;

    public WeaponRequestDto createWeaponRequestDto(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutDamage(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutWeaponType(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithInvalidWeaponType(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(INVALID_WEAPON_TYPE)
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutName(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithNameConsistingOfSpaces(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name("    ")
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithoutDescription(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto createWeaponRequestDtoWithDescriptionConsistingOfSpaces(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description("    ")
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutRequiredLevel(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutRequiredAgility(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutRequiredIntelligence(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutRequiredStrength(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutRarity(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithInvalidRarity(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(INVALID_WEAPON_RARITY)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto createWeaponRequestDtoWithoutBonusIds() {
        return WeaponRequestDto.builder()
            .damage(WEAPON_DAMAGE)
            .weaponType(WEAPON_TYPE.name())
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY.name())
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public Weapon weaponInstance(List<Bonus> bonuses) {
        return Weapon.builder()
            .damage(WEAPON_DAMAGE)
            .bonuses(bonuses)
            .weaponType(WEAPON_TYPE)
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public Weapon weaponInstance() {
        return Weapon.builder()
            .damage(WEAPON_DAMAGE)
            .bonuses(Collections.emptyList())
            .weaponType(WEAPON_TYPE)
            .name(WEAPON_NAME)
            .description(WEAPON_DESCRIPTION)
            .rarity(WEAPON_RARITY)
            .requiredAgility(WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(WEAPON_REQUIRED_LEVEL)
            .requiredStrength(WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDto(List<String> bonusIds) {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(bonusIds)
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutDamage() {
        return WeaponRequestDto.builder()
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutRarity() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithInvalidRarity() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(INVALID_WEAPON_RARITY)
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutWeaponType() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithInvalidWeaponType() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(INVALID_WEAPON_TYPE)
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto updateWeaponRequestDtoWithoutName() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto updateWeaponRequestDtoWithNameConsistingOfSpaces() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name("    ")
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto updateWeaponRequestDtoWithoutDescription() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public static WeaponRequestDto updateWeaponRequestDtoWithDescriptionConsistingOfSpaces() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description("    ")
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutRequiredAgility() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutRequiredIntelligence() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutRequiredLevel() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutRequiredStrength() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .bonusIds(Collections.emptyList())
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .build();
    }

    public WeaponRequestDto updateWeaponRequestDtoWithoutBonusIds() {
        return WeaponRequestDto.builder()
            .damage(UPDATED_WEAPON_DAMAGE)
            .weaponType(UPDATED_WEAPON_TYPE.name())
            .name(UPDATED_WEAPON_NAME)
            .description(UPDATED_WEAPON_DESCRIPTION)
            .rarity(UPDATED_WEAPON_RARITY.name())
            .requiredAgility(UPDATED_WEAPON_REQUIRED_AGILITY)
            .requiredIntelligence(UPDATED_WEAPON_REQUIRED_INTELLIGENCE)
            .requiredLevel(UPDATED_WEAPON_REQUIRED_LEVEL)
            .requiredStrength(UPDATED_WEAPON_REQUIRED_STRENGTH)
            .build();
    }
}
