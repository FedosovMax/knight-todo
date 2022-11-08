package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.TestConstants;
import com.knighttodo.todocore.character.service.privatedb.repository.BonusRepository;
import com.knighttodo.todocore.character.service.privatedb.repository.SkillRepository;
import com.knighttodo.todocore.character.service.privatedb.representation.Bonus;
import com.knighttodo.todocore.character.service.privatedb.representation.Skill;
import com.knighttodo.todocore.character.rest.request.SkillRequestDto;
import com.knighttodo.todocore.factories.BonusFactory;
import com.knighttodo.todocore.factories.SkillFactory;
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

import static com.knighttodo.todocore.Constants.API_BASE_SKILLS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = SkillResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class SkillResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private BonusRepository bonusRepository;

    @AfterEach
    void tearDown() {
        skillRepository.deleteAll();
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
    void addSkill_shouldAddSkillAndReturnSkillResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDto(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(requestDto.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));

        Skill savedSkill = skillRepository.findAll().get(0);

        assertThat(savedSkill.getBonuses()).contains(bonus);
        assertThat(savedSkill.getName()).isEqualTo(requestDto.getName());
        assertThat(savedSkill.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutName(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory
            .createSkillRequestDtoWithNameConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutDescription(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        SkillRequestDto requestDto = SkillFactory
            .createSkillRequestDtoWithDescriptionConsistingOfSpaces(List.of(bonus.getId()));

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void addSkill_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        SkillRequestDto requestDto = SkillFactory.createSkillRequestDtoWithoutBonusIds();

        mockMvc.perform(post(API_BASE_SKILLS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(skillRepository.count()).isEqualTo(0);
    }

    @Test
    void getAllSkills_shouldReturnAllSkillResponseDtos() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill firstSkill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));
        Skill secondSkill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));

        mockMvc.perform(get(API_BASE_SKILLS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2))
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(0)).value(firstSkill.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(0)).value(firstSkill.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(0)).value(firstSkill.getDescription()))
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
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInListByIndex(1)).value(secondSkill.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInListByIndex(1)).value(secondSkill.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescriptionInListByIndex(1)).value(secondSkill.getDescription()))
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
    void getSkillById_shouldReturnSkillResponseDto_whenIdIsCorrect() throws Exception {
        Bonus bonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill skill = skillRepository.save(SkillFactory.skillInstance(List.of(bonus)));

        mockMvc.perform(get(TestConstants.buildGetSkillByIdUrl(skill.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(skill.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(skill.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(skill.getDescription()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToBonuses()).exists())
            .andExpect(jsonPath(TestConstants.buildJsonPathToIdInBonusListByIndex(0)).value(bonus.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToNameInBonusListByIndex(0)).value(bonus.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToRarityInBonusListByIndex(0)).value(bonus.getRarity().toString()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDamageBoostInBonusListByIndex(0)).value(bonus.getDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritChanceBoostInBonusListByIndex(0)).value(bonus.getCritChanceBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToCritDamageBoostInBonusListByIndex(0)).value(bonus.getCritDamageBoost()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToSkillBoostInBonusListByIndex(0)).value(bonus.getSkillBoost()));
    }

    @Test
    void updateSkill_shouldUpdateSkillAndReturnSkillResponseDto_whenRequestIsCorrect() throws Exception {
        Bonus firstBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Bonus secondBonus = bonusRepository.save(BonusFactory.bonusInstance());
        Skill skill = skillRepository.save(SkillFactory.skillInstance(List.of(firstBonus)));

        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDto(List.of(secondBonus.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(skill.getId()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(TestConstants.buildJsonPathToDescription()).value(requestDto.getDescription()))
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

        Skill updatedSkill = skillRepository.findById(skill.getId()).get();

        assertThat(updatedSkill.getName()).isEqualTo(requestDto.getName());
        assertThat(updatedSkill.getDescription()).isEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutName();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenDescriptionIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutDescription();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenDescriptionConsistsOfSpaces() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithDescriptionConsistingOfSpaces();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void updateSkill_shouldRespondWithBadRequestStatus_whenBonusIdsIsNull() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());
        SkillRequestDto requestDto = SkillFactory.updateSkillRequestDtoWithoutBonusIds();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutSkillByIdUrl(skill.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        skill = skillRepository.findById(skill.getId()).get();

        assertThat(skill.getName()).isNotEqualTo(requestDto.getName());
        assertThat(skill.getDescription()).isNotEqualTo(requestDto.getDescription());
    }

    @Test
    void deleteSkill_shouldDeleteSkill_whenIdIsCorrect() throws Exception {
        Skill skill = skillRepository.save(SkillFactory.skillInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteSkillByIdUrl(skill.getId())))
            .andExpect(status().isOk());

        assertThat(skillRepository.findById(skill.getId())).isEmpty();
    }
}
