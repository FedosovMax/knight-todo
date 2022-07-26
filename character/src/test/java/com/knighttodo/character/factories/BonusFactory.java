package com.knighttodo.character.factories;

import com.knighttodo.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.character.gateway.privatedb.representation.enums.Rarity;
import com.knighttodo.character.rest.request.BonusRequestDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BonusFactory {

    private static final String BONUS_NAME = "Some super bonus";
    private static final Rarity BONUS_RARITY = Rarity.LEGEND;
    private static final Integer BONUS_DAMAGE_BOOST = 96;
    private static final Integer BONUS_CRIT_CHANCE_BOOST = 97;
    private static final Integer BONUS_CRIT_DAMAGE_BOOST = 98;
    private static final Integer BONUS_SKILL_BOOST = 99;
    private static final String UPDATED_BONUS_NAME = "Updated super bonus";
    private static final Rarity UPDATED_BONUS_RARITY = Rarity.MYTHICAL;
    private static final Integer UPDATED_BONUS_DAMAGE_BOOST = 1;
    private static final Integer UPDATED_BONUS_CRIT_CHANCE_BOOST = 2;
    private static final Integer UPDATED_BONUS_CRIT_DAMAGE_BOOST = 3;
    private static final Integer UPDATED_BONUS_SKILL_BOOST = 4;

    public static BonusRequestDto createBonusRequestDtoInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutNameInstance() {
        return BonusRequestDto.builder()
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithNameConsistingOfSpacesInstance() {
        return BonusRequestDto.builder()
            .name("    ")
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutRarityInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutCritChanceBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutCritDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto createBonusRequestDtoWithoutSkillBoostInstance() {
        return BonusRequestDto.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY.name())
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .build();
    }

    public static Bonus bonusInstance() {
        return Bonus.builder()
            .name(BONUS_NAME)
            .rarity(BONUS_RARITY)
            .damageBoost(BONUS_DAMAGE_BOOST)
            .critChanceBoost(BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutNameInstance() {
        return BonusRequestDto.builder()
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithNameConsistingOfSpacesInstance() {
        return BonusRequestDto.builder()
            .name("    ")
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutRarityInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .rarity(UPDATED_BONUS_RARITY.name())
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutCritChanceBoostInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutCritDamageBoostInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .skillBoost(UPDATED_BONUS_SKILL_BOOST)
            .build();
    }

    public static BonusRequestDto updateBonusRequestDtoWithoutSkillBoostInstance() {
        return BonusRequestDto.builder()
            .name(UPDATED_BONUS_NAME)
            .rarity(UPDATED_BONUS_RARITY.name())
            .damageBoost(UPDATED_BONUS_DAMAGE_BOOST)
            .critChanceBoost(UPDATED_BONUS_CRIT_CHANCE_BOOST)
            .critDamageBoost(UPDATED_BONUS_CRIT_DAMAGE_BOOST)
            .build();
    }
}
