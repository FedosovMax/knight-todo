package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.TestConstants;
import com.knighttodo.todocore.character.gateway.privatedb.repository.ArmorRepository;
import com.knighttodo.todocore.character.gateway.privatedb.repository.BonusRepository;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Armor;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.todocore.character.rest.request.ArmorRequestDto;
import com.knighttodo.todocore.factories.ArmorFactory;
import com.knighttodo.todocore.factories.BonusFactory;
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

import java.util.List;

import static com.knighttodo.todocore.Constants.API_BASE_ARMORS;
import static com.knighttodo.todocore.Constants.API_BASE_ITEMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@ContextConfiguration(initializers = ArmorResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class ArmorResourceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        armorRepository.deleteAll();
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
    void addArmor_shouldAddArmorAndReturnArmorResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefence()).value(requestDto.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorType()).value(requestDto.getArmorType()))
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

        Armor savedArmor = armorRepository.findAll().get(0);

        assertThat(savedArmor.getBonuses()).contains(bonus);
        assertThat(savedArmor.getDefence()).isEqualTo(requestDto.getDefence());
        Assertions.assertThat(savedArmor.getRarity().name()).isEqualTo(requestDto.getRarity());
        Assertions.assertThat(savedArmor.getArmorType().name()).isEqualTo(requestDto.getArmorType());
        assertThat(savedArmor.getName()).isEqualTo(requestDto.getName());
        assertThat(savedArmor.getDescription()).isEqualTo(requestDto.getDescription());
        assertThat(savedArmor.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(savedArmor.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(savedArmor.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(savedArmor.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDefenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutDefence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutArmorType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithInvalidArmorType(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutName(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithNameConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutDescription(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithDescriptionConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredLevel(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredAgility(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory
            .createArmorRequestDtoWithoutRequiredIntelligence(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRequiredStrength(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithInvalidRarity(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void addArmor_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        ArmorRequestDto requestDto = ArmorFactory.createArmorRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_ITEMS + API_BASE_ARMORS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(armorRepository.count()).isEqualTo(0);
    }

    @Test
    void findAllArmors_shouldReturnAllArmorResponseDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor firstArmor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));
        Armor secondArmor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_ITEMS + API_BASE_ARMORS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(0)).value(firstArmor.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefenceInListByIndex(0)).value(firstArmor.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(0)).value(firstArmor.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorTypeInListByIndex(0)).value(firstArmor.getArmorType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(0)).value(firstArmor.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(0)).value(firstArmor.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(0)).value(firstArmor.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(0))
                .value(firstArmor.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(0)).value(firstArmor.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(0))
                .value(firstArmor.getRequiredStrength()))
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
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(1)).value(secondArmor.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefenceInListByIndex(1)).value(secondArmor.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(1)).value(secondArmor.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorTypeInListByIndex(1)).value(secondArmor.getArmorType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(1)).value(secondArmor.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(1)).value(secondArmor.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgilityInListByIndex(1)).value(secondArmor.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligenceInListByIndex(1))
                .value(secondArmor.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevelInListByIndex(1)).value(secondArmor.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrengthInListByIndex(1))
                .value(secondArmor.getRequiredStrength()))
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
    void findArmorById_shouldReturnArmorResponseDto_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(MockMvcRequestBuilders.get(TestConstants.buildGetArmorByIdUrl(armor.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(armor.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefence()).value(armor.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(armor.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorType()).value(armor.getArmorType().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(armor.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(armor.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredAgility()).value(armor.getRequiredAgility()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredIntelligence()).value(armor.getRequiredIntelligence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredLevel()).value(armor.getRequiredLevel()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRequiredStrength()).value(armor.getRequiredStrength()))
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
    void updateArmor_shouldUpdateArmorAndReturnArmorResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(firstBonus)));

        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(armor.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDefence()).value(requestDto.getDefence()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToArmorType()).value(requestDto.getArmorType()))
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
            .andExpect(
                jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(secondBonus.getCritChanceBoost()))
            .andExpect(
                jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(secondBonus.getSkillBoost()));

        Armor updatedArmor = armorRepository.findById(armor.getId()).get();

        assertThat(updatedArmor.getDefence()).isEqualTo(requestDto.getDefence());
        Assertions.assertThat(updatedArmor.getArmorType().name()).isEqualTo(requestDto.getArmorType());
        assertThat(updatedArmor.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedArmor.getDescription()).isEqualTo(requestDto.getDescription());
        Assertions.assertThat(updatedArmor.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedArmor.getRequiredAgility()).isEqualTo(requestDto.getRequiredAgility());
        assertThat(updatedArmor.getRequiredIntelligence()).isEqualTo(requestDto.getRequiredIntelligence());
        assertThat(updatedArmor.getRequiredLevel()).isEqualTo(requestDto.getRequiredLevel());
        assertThat(updatedArmor.getRequiredStrength()).isEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDefenceIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutDefence();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRarity();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRarityIsInvalid() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithInvalidRarity();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutArmorType();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenArmorTypeIsInvalid() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithInvalidArmorType();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutName();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutDescription();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithDescriptionConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredAgilityIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredAgility();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredIntelligenceIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredIntelligence();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredLevelIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredLevel();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenRequiredStrengthIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutRequiredStrength();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void updateArmor_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());
        ArmorRequestDto requestDto = ArmorFactory.updateArmorRequestDtoWithoutBonusIds();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutArmorByIdUrl(armor.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        armor = armorRepository.findById(armor.getId()).get();

        assertThat(armor.getDefence()).isNotEqualTo(requestDto.getDefence());
        Assertions.assertThat(armor.getArmorType().name()).isNotEqualTo(requestDto.getArmorType());
        assertThat(armor.getName()).isNotEqualTo(requestDto.getName());
        assertThat(armor.getDescription()).isNotEqualTo(requestDto.getDescription());
        Assertions.assertThat(armor.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(armor.getRequiredAgility()).isNotEqualTo(requestDto.getRequiredAgility());
        assertThat(armor.getRequiredIntelligence()).isNotEqualTo(requestDto.getRequiredIntelligence());
        assertThat(armor.getRequiredLevel()).isNotEqualTo(requestDto.getRequiredLevel());
        assertThat(armor.getRequiredStrength()).isNotEqualTo(requestDto.getRequiredStrength());
    }

    @Test
    void deleteArmor_shouldDeleteArmor_whenIdIsCorrect() throws Exception {
        Armor armor = armorRepository.save(ArmorFactory.armorInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteArmorByIdUrl(armor.getId())))
            .andExpect(status().isOk());

        Assertions.assertThat(armorRepository.findById(armor.getId())).isEmpty();
    }

    @Test
    void deleteArmor_shouldDeleteArmorRelatedToExistingBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Armor armor = armorRepository.save(ArmorFactory.armorInstance(List.of(bonus)));

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteArmorByIdUrl(armor.getId())))
            .andExpect(status().isOk());

        Assertions.assertThat(armorRepository.findById(armor.getId())).isEmpty();
    }
}
