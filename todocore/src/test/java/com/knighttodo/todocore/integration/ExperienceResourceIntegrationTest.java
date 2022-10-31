package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.TestConstants;
import com.knighttodo.todocore.character.service.privatedb.repository.CharacterRepository;
import com.knighttodo.todocore.character.rest.request.ExperienceRequestDto;
import com.knighttodo.todocore.factories.CharacterFactory;
import com.knighttodo.todocore.factories.ExperienceFactory;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.knighttodo.todocore.Constants.BASE_EXPERIENCE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ExperienceResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class ExperienceResourceIntegrationTest {

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
    public void calculateExperience_shouldCalculateExpAndReturnCalculatedExp_whenRequestIsCorrect() throws Exception {
        characterRepository.save(CharacterFactory.createCharacterInstance());
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(TestConstants.PARAMETER_EXPERIENCE).value(TestConstants.HARD_SCARY_EXPERIENCE_AMOUNT))
            .andExpect(jsonPath(TestConstants.PARAMETER_TODOID).value(requestDto.getTodoId()));
    }

    @Test
    public void calculateExperience_shouldRespondWithBadRequestStatus_whenTodoIdIsNull() throws Exception {
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoWithoutTodoIdInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void addCharacter_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        ExperienceRequestDto requestDto = ExperienceFactory.experienceRequestDtoWithoutTodoIdInstance();

        mockMvc.perform(post(BASE_EXPERIENCE_URL)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }
}
