package com.knighttodo.character.integration;

import static com.knighttodo.character.Constants.API_BASE_BONUSES;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knighttodo.character.factories.BonusFactory;
import com.knighttodo.character.gateway.privatedb.repository.BonusRepository;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;
import com.knighttodo.character.rest.request.BonusRequestDto;

import com.knighttodo.character.TestConstants;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = BonusResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class BonusResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
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
    void addBonus_shouldAddBonusAndReturnIt_whenRequestIsCorrect() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoost()).value(requestDto.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoost()).value(requestDto.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoost()).value(requestDto.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoost()).value(requestDto.getSkillBoost()));

        assertThat(bonusRepository.count()).isEqualTo(1);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutNameInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithNameConsistingOfSpacesInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutRarityInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenRarityIsNotValid() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoInstance();
        String request = objectMapper.writeValueAsString(requestDto).replace(requestDto.getRarity(), "MYSTIC ORANGE");

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritChanceBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutCritChanceBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenCritDamageBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutCritDamageBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void addBonus_shouldRespondWithBadRequestStatus_whenSkillBoostIsNull() throws Exception {
        BonusRequestDto requestDto = BonusFactory.createBonusRequestDtoWithoutSkillBoostInstance();

        mockMvc.perform(post(API_BASE_BONUSES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(bonusRepository.count()).isEqualTo(0);
    }

    @Test
    void getAllBonuses_shouldReturnAllBonuses() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(get(API_BASE_BONUSES))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(0)).value(firstBonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(0)).value(firstBonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(0)).value(firstBonus.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInListByIndex(0)).value(firstBonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInListByIndex(0)).value(firstBonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInListByIndex(0)).value(firstBonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInListByIndex(0)).value(firstBonus.getSkillBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(1)).value(secondBonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(1)).value(secondBonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInListByIndex(1)).value(secondBonus.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInListByIndex(1)).value(secondBonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInListByIndex(1)).value(secondBonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInListByIndex(1)).value(secondBonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInListByIndex(1)).value(secondBonus.getSkillBoost()));
    }

    @Test
    void getBonusById_shouldReturnExistingBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(MockMvcRequestBuilders.get(TestConstants.buildGetBonusByIdUrl(bonus.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(bonus.getRarity().name()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoost()).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoost()).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoost()).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoost()).value(bonus.getSkillBoost()));
    }

    @Test
    void updateBonus_shouldUpdateBonusAndReturnIt_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarity()).value(requestDto.getRarity()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoost()).value(requestDto.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoost()).value(requestDto.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoost()).value(requestDto.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoost()).value(requestDto.getSkillBoost()));

        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(updatedBonus.getName()).isEqualTo(requestDto.getName());
        Assertions.assertThat(updatedBonus.getRarity().name()).isEqualTo(requestDto.getRarity());
        assertThat(updatedBonus.getDamageBoost()).isEqualTo(requestDto.getDamageBoost());
        assertThat(updatedBonus.getCritChanceBoost()).isEqualTo(requestDto.getCritChanceBoost());
        assertThat(updatedBonus.getCritDamageBoost()).isEqualTo(requestDto.getCritDamageBoost());
        assertThat(updatedBonus.getSkillBoost()).isEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutNameInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithNameConsistingOfSpacesInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenRarityIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutRarityInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenRarityIsNotValid() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoInstance();
        String request = objectMapper.writeValueAsString(requestDto).replace(requestDto.getRarity(), "MYSTIC ORANGE");

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenDamageBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutDamageBoostInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenCritChanceBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutCritChanceBoostInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenCritDamageBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutCritDamageBoostInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void updateBonus_shouldRespondWithBadRequestStatus_whenSkillBoostIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        BonusRequestDto requestDto = BonusFactory.updateBonusRequestDtoWithoutSkillBoostInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutBonusByIdUrl(bonus.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        bonus = bonusRepository.findById(bonus.getId()).get();

        assertThat(bonus.getName()).isNotEqualTo(requestDto.getName());
        Assertions.assertThat(bonus.getRarity().name()).isNotEqualTo(requestDto.getRarity());
        assertThat(bonus.getDamageBoost()).isNotEqualTo(requestDto.getDamageBoost());
        assertThat(bonus.getCritChanceBoost()).isNotEqualTo(requestDto.getCritChanceBoost());
        assertThat(bonus.getCritDamageBoost()).isNotEqualTo(requestDto.getCritDamageBoost());
        assertThat(bonus.getSkillBoost()).isNotEqualTo(requestDto.getSkillBoost());
    }

    @Test
    void deleteBonus_shouldDeleteBonus_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteBonusByIdUrl(bonus.getId())))
            .andExpect(status().isOk());

        assertThat(bonusRepository.findById(bonus.getId())).isEmpty();
    }
}
