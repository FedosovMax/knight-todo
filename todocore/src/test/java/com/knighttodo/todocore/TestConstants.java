package com.knighttodo.todocore;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import static com.knighttodo.todocore.Constants.*;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_FALSE = "false";
    public static final String PARAMETER_TRUE = "true";
    public static final String ID_JSON_PATH = ".id";
    public static final String LOGIN_JSON_PATH = ".login";
    public static final String LENGTH_JSON_PATH = ".length()";

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

    public static String buildIdJsonPath() {
        return JSON_ROOT + ID_JSON_PATH;
    }
    public static String buildLoginJsonPath() {
        return JSON_ROOT + LOGIN_JSON_PATH;
    }
    public static String buildGetDayByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + id;
    }
    public static String buildGetDayByDate(LocalDate date) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/date?date=" + date.toString();
    }
    public static String buildGetDayByDateWithIncorrectDate(String date) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/date?date=" + date;
    }

    public static String buildGetDayByDateWithoutDate(String date) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/date?date=" + date;
    }

    public static String buildDeleteDayByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + id;
    }
    public static String buildGetDayTodoByIdUrl(UUID dayId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }
    public static String buildGetRoutineTodoByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }
    public static String buildGetRoutineByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + id;
    }
    public static String buildGetRoutineInstanceByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_ROUTINES_INSTANCES + "/" + id;
    }
    public static String buildDeleteRoutineByIdUrl(UUID routineId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId;
    }
    public static String buildDeleteRoutineInstanceByIdUrl(UUID routineId, UUID routineInstanceId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_ROUTINES_INSTANCES + "/" + routineInstanceId;
    }
    public static String buildDeleteTodoByIdUrl(UUID dayId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }
    public static String buildDeleteRoutineTodoByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }
    public static String buildGetTodosByDayIdUrl(UUID dayId) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS;
    }
    public static String buildGetRoutineTodosByDayIdUrl(UUID routineId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS;
    }
    public static String buildUpdateTodoReadyBaseUrl(UUID dayId, UUID dayTodoId) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + dayTodoId + BASE_READY;
    }
    public static String buildUpdateRoutineTodoReadyBaseUrl(UUID routineId, UUID routineTodoId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + routineTodoId + BASE_READY;
    }
    public static String buildJsonPathToId() {
        return JSON_ROOT + "id";
    }
    public static String buildJsonPathToIdDate() {
        return JSON_ROOT + ".id";
    }
    public static String buildJsonPathToDate() {
        return JSON_ROOT + ".date";
    }
    public static String buildJsonPathToLength() {
        return JSON_ROOT + "length()";
    }
    public static String buildJsonPathToTodoName() {
        return JSON_ROOT + "dayTodoName";
    }
    public static String buildJsonPathToRoutineTodoName() {
        return JSON_ROOT + "routineTodoName";
    }
    public static String buildJsonPathToName() {
        return JSON_ROOT + "name";
    }
    public static String buildJsonPathToScariness() {
        return JSON_ROOT + "scariness";
    }
    public static String buildJsonPathToHardness() {
        return JSON_ROOT + "hardness";
    }

    public static String buildJsonPathToOrderNumber() {
        return JSON_ROOT + "orderNumber";
    }
    public static String buildJsonPathToColor() {
        return JSON_ROOT +"color";

    }
    public static String buildJsonPathToDayId() {
        return JSON_ROOT + "dayId";
    }
    public static String buildJsonPathToRoutineId() {
        return JSON_ROOT + "routineId";
    }
    public static String buildJsonPathToRoutineInstanceId() {
        return JSON_ROOT + "routineInstance";
    }
    public static String buildJsonPathToExperience() {
        return JSON_ROOT + "experience";
    }
    public static String buildJsonPathToDayName() {
        return JSON_ROOT + "dayName";
    }
    public static String buildJsonPathToReadyName() {
        return JSON_ROOT + "ready";
    }
    public static String buildJsonPathToRoutinesName() {
        return JSON_ROOT + "routines";
    }
    public static String buildJsonPathToRoutineTodoIdInTodosListByIndex(int index) {
        return JSON_ROOT + "routineTodos.[" + index + "].id";
    }
    public static String buildJsonPathToRoutineInstanceIdInInstancesListByIndex(int index) {
        return JSON_ROOT + "routineInstances.[" + index + "].id";
    }
    public static String buildJsonPathToRoutineIdInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].id";
    }
    public static String buildJsonPathToRoutineNameInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].name";
    }
    public static String buildJsonPathToRoutineHardnessInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].hardness";
    }
    public static String buildJsonPathToRoutineScarinessInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].scariness";
    }
    public static String buildJsonPathToRoutineReadyInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].ready";
    }
    public static String buildJsonPathToTodoIdInTodosListInRoutinesListByIndexes(int routineIndex, int todoIndex) {
        return JSON_ROOT + "routines.[" + routineIndex + "].todos[" + todoIndex + "].id";
    }

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

    public static String buildJsonPathToCharacterName() {
        return JSON_ROOT + PARAMETER_CHARACTER_NAME;
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
