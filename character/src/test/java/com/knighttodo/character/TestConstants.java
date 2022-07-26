package com.knighttodo.character;

import static com.knighttodo.character.Constants.API_BASE_ARMORS;
import static com.knighttodo.character.Constants.API_BASE_BONUSES;
import static com.knighttodo.character.Constants.API_BASE_ITEMS;
import static com.knighttodo.character.Constants.API_BASE_SKILLS;
import static com.knighttodo.character.Constants.API_BASE_WEAPONS;
import static com.knighttodo.character.Constants.BASE_CHARACTER;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_ID = "id";
    public static final String PARAMETER_LENGTH = "length()";
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_RARITY = "rarity";
    public static final String PARAMETER_DAMAGE_BOOST = "damageBoost";
    public static final String PARAMETER_CRIT_CHANCE_BOOST = "critChanceBoost";
    public static final String PARAMETER_CRIT_DAMAGE_BOOST = "critDamageBoost";
    public static final String PARAMETER_SKILL_BOOST = "skillBoost";
    public static final String PARAMETER_CHARACTER_NAME = "characterName";
    public static final String PARAMETER_EXPERIENCE = "experience";
    public static final String PARAMETER_TODOID = "todoId";
    public static final String PARAMETER_DESCRIPTION = "description";
    public static final String PARAMETER_BONUSES = "bonuses";
    public static final String PARAMETER_DAMAGE = "damage";
    public static final String PARAMETER_WEAPON_TYPE = "weaponType";
    public static final String PARAMETER_REQUIRED_AGILITY = "requiredAgility";
    public static final String PARAMETER_REQUIRED_INTELLIGENCE = "requiredIntelligence";
    public static final String PARAMETER_REQUIRED_LEVEL = "requiredLevel";
    public static final String PARAMETER_REQUIRED_STRENGTH = "requiredStrength";
    public static final String PARAMETER_DEFENCE = "defence";
    public static final String PARAMETER_ARMOR_TYPE = "armorType";

    public static final Integer HARD_SCARY_EXPERIENCE_AMOUNT = 13;

    public static String buildGetCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildPutCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildDeleteCharacterByIdUrl(String characterId) {
        return BASE_CHARACTER + "/" + characterId;
    }

    public static String buildGetBonusByIdUrl(String bonusId) {
        return API_BASE_BONUSES + "/" + bonusId;
    }

    public static String buildPutBonusByIdUrl(String bonusId) {
        return API_BASE_BONUSES + "/" + bonusId;
    }

    public static String buildDeleteBonusByIdUrl(String bonusId) {
        return API_BASE_BONUSES + "/" + bonusId;
    }

    public static String buildGetSkillByIdUrl(String skillId) {
        return API_BASE_SKILLS + "/" + skillId;
    }

    public static String buildPutSkillByIdUrl(String skillId) {
        return API_BASE_SKILLS + "/" + skillId;
    }

    public static String buildDeleteSkillByIdUrl(String skillId) {
        return API_BASE_SKILLS + "/" + skillId;
    }

    public static String buildGetWeaponByIdUrl(String weaponId) {
        return API_BASE_ITEMS + API_BASE_WEAPONS + "/" + weaponId;
    }

    public static String buildPutWeaponByIdUrl(String weaponId) {
        return API_BASE_ITEMS + API_BASE_WEAPONS + "/" + weaponId;
    }

    public static String buildDeleteWeaponByIdUrl(String weaponId) {
        return API_BASE_ITEMS + API_BASE_WEAPONS + "/" + weaponId;
    }

    public static String buildGetArmorByIdUrl(String armorId) {
        return API_BASE_ITEMS + API_BASE_ARMORS + "/" + armorId;
    }

    public static String buildPutArmorByIdUrl(String armorId) {
        return API_BASE_ITEMS + API_BASE_ARMORS + "/" + armorId;
    }

    public static String buildDeleteArmorByIdUrl(String armorId) {
        return API_BASE_ITEMS + API_BASE_ARMORS + "/" + armorId;
    }

    public static String buildJsonPathToId() {
        return JSON_ROOT + PARAMETER_ID;
    }

    public static String buildJsonPathToLength() {
        return JSON_ROOT + PARAMETER_LENGTH;
    }

    public static String buildJsonPathToCharacterName() {
        return JSON_ROOT + PARAMETER_CHARACTER_NAME;
    }

    public static String buildJsonPathToName() {
        return JSON_ROOT + PARAMETER_NAME;
    }

    public static String buildJsonPathToRarity() {
        return JSON_ROOT + PARAMETER_RARITY;
    }

    public static String buildJsonPathToDamageBoost() {
        return JSON_ROOT + PARAMETER_DAMAGE_BOOST;
    }

    public static String buildJsonPathToCritChanceBoost() {
        return JSON_ROOT + PARAMETER_CRIT_CHANCE_BOOST;
    }

    public static String buildJsonPathToCritDamageBoost() {
        return JSON_ROOT + PARAMETER_CRIT_DAMAGE_BOOST;
    }

    public static String buildJsonPathToSkillBoost() {
        return JSON_ROOT + PARAMETER_SKILL_BOOST;
    }

    public static String buildJsonPathToIdInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_ID;
    }

    public static String buildJsonPathToNameInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_NAME;
    }

    public static String buildJsonPathToRarityInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_RARITY;
    }

    public static String buildJsonPathToDamageBoostInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_DAMAGE_BOOST;
    }

    public static String buildJsonPathToCritChanceBoostInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_CRIT_CHANCE_BOOST;
    }

    public static String buildJsonPathToCritDamageBoostInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_CRIT_DAMAGE_BOOST;
    }

    public static String buildJsonPathToSkillBoostInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_SKILL_BOOST;
    }

    public static String buildJsonPathToDescription() {
        return JSON_ROOT + PARAMETER_DESCRIPTION;
    }

    public static String buildJsonPathToBonuses() {
        return JSON_ROOT + PARAMETER_BONUSES;
    }

    public static String buildJsonPathToBonusesLength() {
        return JSON_ROOT + PARAMETER_BONUSES + "." + PARAMETER_LENGTH;
    }

    public static String buildJsonPathToIdInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_ID;
    }

    public static String buildJsonPathToNameInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_NAME;
    }

    public static String buildJsonPathToRarityInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_RARITY;
    }

    public static String buildJsonPathToDamageBoostInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_DAMAGE_BOOST;
    }

    public static String buildJsonPathToCritChanceBoostInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_CRIT_CHANCE_BOOST;
    }

    public static String buildJsonPathToCritDamageBoostInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_CRIT_DAMAGE_BOOST;
    }

    public static String buildJsonPathToSkillBoostInBonusListByIndex(int index) {
        return JSON_ROOT + PARAMETER_BONUSES + "[" + index + "]." + PARAMETER_SKILL_BOOST;
    }

    public static String buildJsonPathToDescriptionInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_DESCRIPTION;
    }

    public static String buildJsonPathToBonusesInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_BONUSES;
    }

    public static String buildJsonPathToIdInBonusListNestedInSkillListByIndexes(int skillIndex, int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]." + PARAMETER_ID;
    }

    public static String buildJsonPathToNameInBonusListNestedInSkillListByIndexes(int skillIndex, int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]." + PARAMETER_NAME;
    }

    public static String buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(int skillIndex, int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]." + PARAMETER_RARITY;
    }

    public static String buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(int skillIndex,
        int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]."
            + PARAMETER_DAMAGE_BOOST;
    }

    public static String buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(int skillIndex,
        int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]."
            + PARAMETER_CRIT_CHANCE_BOOST;
    }

    public static String buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(int skillIndex,
        int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]."
            + PARAMETER_CRIT_DAMAGE_BOOST;
    }

    public static String buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(int skillIndex,
        int bonusIndex) {
        return JSON_ROOT + "[" + skillIndex + "]." + PARAMETER_BONUSES + "[" + bonusIndex + "]."
            + PARAMETER_SKILL_BOOST;
    }

    public static String buildJsonPathToDamage() {
        return JSON_ROOT + PARAMETER_DAMAGE;
    }

    public static String buildJsonPathToWeaponType() {
        return JSON_ROOT + PARAMETER_WEAPON_TYPE;
    }

    public static String buildJsonPathToRequiredAgility() {
        return JSON_ROOT + PARAMETER_REQUIRED_AGILITY;
    }

    public static String buildJsonPathToRequiredIntelligence() {
        return JSON_ROOT + PARAMETER_REQUIRED_INTELLIGENCE;
    }

    public static String buildJsonPathToRequiredLevel() {
        return JSON_ROOT + PARAMETER_REQUIRED_LEVEL;
    }

    public static String buildJsonPathToRequiredStrength() {
        return JSON_ROOT + PARAMETER_REQUIRED_STRENGTH;
    }

    public static String buildJsonPathToDamageInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_DAMAGE;
    }

    public static String buildJsonPathToWeaponTypeInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_WEAPON_TYPE;
    }

    public static String buildJsonPathToRequiredAgilityInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_REQUIRED_AGILITY;
    }

    public static String buildJsonPathToRequiredIntelligenceInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_REQUIRED_INTELLIGENCE;
    }

    public static String buildJsonPathToRequiredLevelInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_REQUIRED_LEVEL;
    }

    public static String buildJsonPathToRequiredStrengthInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_REQUIRED_STRENGTH;
    }

    public static String buildJsonPathToDefence() {
        return JSON_ROOT + PARAMETER_DEFENCE;
    }

    public static String buildJsonPathToArmorType() {
        return JSON_ROOT + PARAMETER_ARMOR_TYPE;
    }

    public static String buildJsonPathToDefenceInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_DEFENCE;
    }

    public static String buildJsonPathToArmorTypeInListByIndex(int index) {
        return JSON_ROOT + "[" + index + "]." + PARAMETER_ARMOR_TYPE;
    }
}
