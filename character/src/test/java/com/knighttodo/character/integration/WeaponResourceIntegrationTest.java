package com.knighttodo.character.integration;

import static com.knighttodo.character.Constants.API_BASE_ITEMS;
import static com.knighttodo.character.Constants.API_BASE_WEAPONS;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knighttodo.character.factories.BonusFactory;
import com.knighttodo.character.factories.WeaponFactory;
import com.knighttodo.character.gateway.privatedb.repository.BonusRepository;
import com.knighttodo.character.gateway.privatedb.repository.WeaponRepository;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.character.gateway.privatedb.representation.Weapon;
import com.knighttodo.character.rest.request.WeaponRequestDto;

import java.util.List;

import com.knighttodo.character.TestConstants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@ContextConfiguration(initializers = WeaponResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class WeaponResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
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
    @Transactional
    void addWeapon_shouldAddWeaponAndReturnWeaponResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamage()).value(requestDto.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponType()).value(requestDto.getWeaponType()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(requestDto.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgility()).value(requestDto.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligence()).value(requestDto.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevel()).value(requestDto.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrength()).value(requestDto.getRequiredStrength()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));

        Weapon savedWeapon = weaponRepository.findAll().get(0);

        assertThat(savedWeapon.getBonuses()).contains(bonus);
        assertThat(savedWeapon.getDamage()).isEqualTo(requestDto.getDamage());
        assertThat(savedWeapon.getRarity().name()).isEqualTo(requestDto.getRarity());
        Assertions.assertThat(savedWeapon.getWeaponType().name()).isEqualTo(requestDto.getWeaponType());
        assertThat(savedWeapon.getName()).isEqualTo(requestDto.getName());
        assertThat(savedWeapon.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(savedWeapon.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(savedWeapon.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(savedWeapon.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(savedWeapon.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenDamageIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutDamage(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidWeaponType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutName(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithNameConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutDescription(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithDescriptionConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRequiredLevel(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredAgility(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredIntelligence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory
            .createWeaponRequestDtoWithoutRequiredStrength(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithInvalidRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void addWeapon_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        WeaponRequestDto requestDto = WeaponFactory.createWeaponRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_WEAPONS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(weaponRepository.count()).isEqualTo(0);
    }

    @Test
    void findAllWeapons_shouldReturnAllWeaponResponseDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon firstWeapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));
        Weapon secondWeapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS + API_BASE_WEAPONS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(0)).value(firstWeapon.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageInListByIndex(0)).value(firstWeapon.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(0)).value(firstWeapon.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponTypeInListByIndex(0)).value(firstWeapon.getWeaponType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(0)).value(firstWeapon.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(0)).value(firstWeapon.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(0)).value(firstWeapon.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(firstWeapon.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(0)).value(firstWeapon.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(0))
                .value(firstWeapon.getRequiredStrength()))
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
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(1)).value(secondWeapon.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageInListByIndex(1)).value(secondWeapon.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(1)).value(secondWeapon.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponTypeInListByIndex(1)).value(secondWeapon.getWeaponType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(1)).value(secondWeapon.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(1)).value(secondWeapon.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(1))
                .value(secondWeapon.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(secondWeapon.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(1)).value(secondWeapon.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(1))
                .value(secondWeapon.getRequiredStrength()))
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

    @Test
    void findWeaponById_shouldReturnWeaponResponseDto_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(MockMvcRequestBuilders.get(TestConstants.buildGetWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(weapon.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamage()).value(weapon.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(weapon.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponType()).value(weapon.getWeaponType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(weapon.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(weapon.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgility()).value(weapon.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligence()).value(weapon.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevel()).value(weapon.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrength()).value(weapon.getRequiredStrength()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonusesLength()).value(1))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));
    }

    @Test
    void updateWeapon_shouldUpdateWeaponAndReturnWeaponResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(firstBonus)));

        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(weapon.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamage()).value(requestDto.getDamage()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToWeaponType()).value(requestDto.getWeaponType()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(requestDto.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgility()).value(requestDto.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligence()).value(requestDto.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevel()).value(requestDto.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrength()).value(requestDto.getRequiredStrength()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonusesLength()).value(1))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListByIndex(0)).value(secondBonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListByIndex(0)).value(secondBonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListByIndex(0)).value(secondBonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListByIndex(0)).value(secondBonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0))
                .value(secondBonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0))
                .value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(secondBonus.getSkillBoost()));

        Weapon updatedWeapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(updatedWeapon.getDamage()).isEqualTo(requestDto.getDamage());
        Assertions.assertThat(updatedWeapon.getWeaponType().name()).isEqualTo(requestDto.getWeaponType());
        assertThat(updatedWeapon.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedWeapon.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(updatedWeapon.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedWeapon.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(updatedWeapon.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(updatedWeapon.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(updatedWeapon.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenDamageIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutDamage();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRarity();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithInvalidRarity();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutWeaponType();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenWeaponTypeIsInvalid() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithInvalidWeaponType();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutName();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutDescription();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithDescriptionConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredAgility();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredIntelligence();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredLevel();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutRequiredStrength();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateWeapon_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());
        WeaponRequestDto requestDto = WeaponFactory.updateWeaponRequestDtoWithoutBonusIds();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutWeaponByIdUrl(weapon.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        weapon = weaponRepository.findById(weapon.getId()).get();

        assertThat(weapon.getDamage()).isNotEqualTo(requestDto.getDamage());
        Assertions.assertThat(weapon.getWeaponType().name()).isNotEqualTo(requestDto.getWeaponType());
        assertThat(weapon.getName()).isNotEqualTo(requestDto.getName());
        assertThat(weapon.getDescription()).isNotEqualTo(requestDto.getDescription());
        assertThat(weapon.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(weapon.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(weapon.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(weapon.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(weapon.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void deleteWeapon_shouldDeleteWeapon_whenIdIsCorrect() throws Exception {
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isOk());

        Assertions.assertThat(weaponRepository.findById(weapon.getId())).isEmpty();
    }

    @Test
    void deleteWeapon_shouldDeleteWeaponRelatedToExistingBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Weapon weapon = weaponRepository.save(WeaponFactory.weaponInstance(List.of(bonus)));

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteWeaponByIdUrl(weapon.getId())))
            .andExpect(status().isOk());

        Assertions.assertThat(weaponRepository.findById(weapon.getId())).isEmpty();
    }
}
