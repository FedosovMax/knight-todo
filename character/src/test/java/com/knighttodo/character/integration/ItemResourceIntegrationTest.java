package com.knighttodo.character.integration;

import static com.knighttodo.character.Constants.API_BASE_ITEMS;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.knighttodo.character.factories.ArmorFactory;
import com.knighttodo.character.factories.BonusFactory;
import com.knighttodo.character.factories.WeaponFactory;
import com.knighttodo.character.gateway.privatedb.repository.ArmorRepository;
import com.knighttodo.character.gateway.privatedb.repository.BonusRepository;
import com.knighttodo.character.gateway.privatedb.repository.WeaponRepository;
import com.knighttodo.character.gateway.privatedb.representation.Armor;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.character.gateway.privatedb.representation.Weapon;

import java.util.List;

import com.knighttodo.character.TestConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@ContextConfiguration(initializers = ItemResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class ItemResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        armorRepository.deleteAll();
        weaponRepository.deleteAll();
        bonusRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres");

    static {
        postgresqlContainer.start();
    }

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword()
            );
        }
    }

    @Test
    void findAllItems_shouldReturnAllItemResponseInheritorDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(0)).value(armor.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefenceInListByIndex(0)).value(armor.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(0)).value(armor.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorTypeInListByIndex(0)).value(armor.getArmorType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(0)).value(armor.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(0)).value(armor.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(0)).value(armor.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(armor.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(0)).value(armor.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(0)).value(armor.getRequiredStrength()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonusesInListByIndex(0)).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes(0, 0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(0, 0))
                .value(bonus.getSkillBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(1)).value(weapon.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageInListByIndex(1)).value(weapon.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(1)).value(weapon.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponTypeInListByIndex(1)).value(weapon.getWeaponType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(1)).value(weapon.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(1)).value(weapon.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(1)).value(weapon.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(weapon.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(1)).value(weapon.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(1)).value(weapon.getRequiredStrength()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonusesInListByIndex(1)).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListNestedInSkillListByIndexes(1, 0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListNestedInSkillListByIndexes(1, 0))
                .value(bonus.getSkillBoost()));
    }
}
