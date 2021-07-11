package com.knighttodo.knighttodo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.factories.DayTodoFactory;
import com.knighttodo.knighttodo.factories.RoutineInstanceFactory;
import com.knighttodo.knighttodo.factories.RoutineTodoFactory;
import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineTodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.knighttodo.knighttodo.Constants.*;
import static com.knighttodo.knighttodo.TestConstants.*;
import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = RoutineTodoResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class RoutineTodoResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoutineTodoRepository routineTodoRepository;

    @Autowired
    private RoutineInstanceRepository routineInstanceRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${baseUrl.experience}")
    private String experienceUrl;

    @AfterEach
    public void tearDown() {
        routineTodoRepository.deleteAll();
        routineInstanceRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:11.1");

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
    public void addRoutineTodo_shouldAddTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDto();

        mockMvc.perform(
                post(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(buildJsonPathToRoutineId()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineTodoRepository.count()).isEqualTo(1);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutName();

        mockMvc.perform(
                post(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(post(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutScariness();

        mockMvc.perform(post(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutHardness();

        mockMvc.perform(post(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void findAllRoutineInstanceTodos_shouldReturnAllRoutineTodos() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));

        mockMvc.perform(get(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void findRoutineTodoById_shouldReturnExistingRoutineTodo_whenIdIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance);
        RoutineTodo savedRoutineTodo = routineTodoRepository.save(routineTodo);

        mockMvc.perform(get(buildGetRoutineTodoByIdUrl(routineInstance.getId(), savedRoutineTodo.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToId()).value(routineTodo.getId()));
    }

    @Test
    public void updateRoutineTodo_shouldUpdateRoutineTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDto();

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToRoutineTodoName()).value(requestDto.getRoutineTodoName()))
                .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
                .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()));

        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().getRoutineTodoName())
                .isEqualTo(requestDto.getRoutineTodoName());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutHardness();

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory
                .updateRoutineTodoRequestDtoWithNameConsistingOfSpaces(routineInstance);

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestDtoWithoutScariness(routineInstance);

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestDtoWithoutHardness(routineInstance);

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldUpdateReadyTodoAndReturnIt_whenScarinessAndHardnessAreUnchanged() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDto();

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToRoutineTodoName()).value(requestDto.getRoutineTodoName()))
                .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
                .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()));

        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().getScariness()).isEqualTo(requestDto.getScariness());
        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().getHardness()).isEqualTo(requestDto.getHardness());
    }

    @Test
    public void updateRoutineTodo_shouldThrowUnchangeableFieldUpdateException_whenRoutineTodoWasReadyAndScarinessWasChanged() {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineReadyInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDtoWithChangedScariness();

        try {
            mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception was't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update routine todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().getScariness()).isEqualTo(routineTodo.getScariness());
    }

    @Test
    public void updateRoutineTodo_shouldThrowUnchangeableFieldUpdateException_whenRoutineTodoWasReadyAndHardnessWasChanged() {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineReadyInstance(routineInstance));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDtoWithChangedHardness();

        try {
            mockMvc.perform(put(API_BASE_ROUTINES + "/" + routineInstance.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception was't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update routine todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().getHardness()).isEqualTo(routineTodo.getHardness());
    }

    @Test
    @Transactional
    public void deleteRoutineTodo_shouldDeleteRoutineTodo_whenIdIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        routineTodoRepository.save(routineTodo);

        mockMvc.perform(delete(buildDeleteRoutineTodoByIdUrl(routineInstance.getId(), routineTodo.getId())))
                .andExpect(status().isOk());

        assertThat(routineTodoRepository.findById(routineTodo.getId())).isEmpty();
        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void findRoutineTodosByRoutineId_shouldReturnExistingRoutineTodo_whenIdIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));

        mockMvc.perform(get(buildGetRoutineTodosByDayIdUrl(routineInstance.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyTrue_whenRoutineTodoIdIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineInstance(routineInstance));
        ExperienceResponse experienceResponse = RoutineTodoFactory.experienceResponseInstance(routineTodo.getId());

        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
                .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));

        mockMvc.perform(put(buildUpdateRoutineTodoReadyBaseUrl(routineInstance.getId(), routineTodo.getId()))
                .param(PARAM_READY, PARAMETER_TRUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToRoutineId()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToExperience()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToReadyName()).value(true));

        assertThat(routineTodoRepository.findById(routineTodo.getId()).get().isReady()).isEqualTo(true);
    }

    @Test
    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyFalse_whenRoutineTodoIdIsCorrect() throws Exception {
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
        RoutineTodo routineTodoWithReadyTrue = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineReadyInstance(routineInstance));
        ExperienceResponse experienceResponse = DayTodoFactory.experienceResponseInstance(routineTodoWithReadyTrue.getId());

        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
                .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));

        mockMvc.perform(put(buildUpdateRoutineTodoReadyBaseUrl(routineInstance.getId(), routineTodoWithReadyTrue.getId()))
                .param(PARAM_READY, PARAMETER_FALSE))
                .andExpect(status().isOk());

        assertThat(routineTodoRepository.findById(routineTodoWithReadyTrue.getId()).get().isReady()).isEqualTo(false);
    }
}
