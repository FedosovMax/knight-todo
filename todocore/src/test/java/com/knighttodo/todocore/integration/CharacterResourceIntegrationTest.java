package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.TestConstants;
import com.knighttodo.todocore.character.service.privatedb.repository.CharacterRepository;
import com.knighttodo.todocore.character.service.privatedb.representation.Character;
import com.knighttodo.todocore.character.rest.request.CharacterRequestDto;
import com.knighttodo.todocore.factories.CharacterFactory;
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

import static com.knighttodo.todocore.Constants.BASE_CHARACTER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = CharacterResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class CharacterResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CharacterRepository characterRepository;

    @AfterEach
    public void tearDown() {
        characterRepository.deleteAll();
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
    public void addCharacter_shouldAddCharacterAndReturnIt_whenRequestIsCorrect() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).exists());

        assertThat(characterRepository.count()).isEqualTo(1);
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNullNameInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(characterRepository.count()).isEqualTo(0);
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNameOfSpacesInstance();

        mockMvc.perform(post(BASE_CHARACTER)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(characterRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllCharacters_shouldReturnTwoCharacters() throws Exception {
        characterRepository.save(CharacterFactory.createCharacterInstance());
        characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(get(BASE_CHARACTER))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToLength()).value(2));
    }

    @Test
    public void getCharacterById_shouldReturnExistingCharacter_whenIdIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(get(TestConstants.buildGetCharacterByIdUrl(character.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(TestConstants.buildJsonPathToId()).value(character.getId()));
    }

    @Test
    public void updateCharacter_shouldUpdateCharacterAndReturnIt_whenRequestIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.buildJsonPathToCharacterName()).value(requestDto.getCharacterName()));

        assertThat(characterRepository.findById(character.getId()).get().getName())
            .isEqualTo(requestDto.getCharacterName());
    }

    @Test
    public void updateCharacter_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNullNameInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCharacter_shouldRespondWithBadRequestStatus_whenIdConsistsOfSpaces() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());
        CharacterRequestDto requestDto = CharacterFactory.characterRequestDtoWithNameOfSpacesInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(TestConstants.buildPutCharacterByIdUrl(character.getId()))
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCharacter_shouldDeleteCharacter_whenIdIsCorrect() throws Exception {
        Character character = characterRepository.save(CharacterFactory.createCharacterInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(TestConstants.buildDeleteCharacterByIdUrl(character.getId())))
            .andExpect(status().isOk());

        assertThat(characterRepository.findById(character.getId())).isEmpty();
    }
}
