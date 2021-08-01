package com.knighttodo.knighttodo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.factories.RoutineFactory;
import com.knighttodo.knighttodo.factories.RoutineInstanceFactory;
import com.knighttodo.knighttodo.factories.RoutineTodoFactory;
import com.knighttodo.knighttodo.factories.RoutineTodoInstanceFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineTodoInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineTodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
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

    @Autowired
    private RoutineTodoInstanceRepository routineTodoInstanceRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @MockBean
    private RestTemplate restTemplate;

    @AfterEach
    public void tearDown() {
        routineTodoRepository.deleteAll();
        routineRepository.deleteAll();
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
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDto();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(buildJsonPathToRoutineId()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineTodoRepository.count()).isEqualTo(1);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutName();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutScariness();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addRoutineTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutHardness();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void findAllRoutineInstanceTodos_shouldReturnAllRoutineTodos() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));

        mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void findRoutineTodoById_shouldReturnExistingRoutineTodo_whenIdIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = RoutineTodoFactory.routineTodoWithRoutine(routine);
        RoutineTodo savedRoutineTodo = routineTodoRepository.save(routineTodo);

        mockMvc.perform(get(buildGetRoutineTodoByIdUrl(routine.getId(), savedRoutineTodo.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToId()).value(routineTodo.getId().toString()));
    }

    @Test
    public void updateRoutineTodo_shouldUpdateRoutineTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDto();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() + API_BASE_TODOS + "/" + routineTodo.getId())
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
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.createRoutineTodoRequestDtoWithoutHardness();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId()
                + API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory
                .updateRoutineTodoRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestDtoWithoutScariness();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestDtoWithoutHardness();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                API_BASE_TODOS + "/" + routineTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateRoutineTodo_shouldUpdateReadyTodoAndReturnIt_whenScarinessAndHardnessAreUnchanged() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDto();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                API_BASE_TODOS + "/" + routineTodo.getId())
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
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineReadyInstance(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDtoWithChangedScariness();

        try {
            mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                    API_BASE_TODOS + "/" + routineTodo.getId())
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
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutineReadyInstance(routine));
        RoutineTodoRequestDto requestDto = RoutineTodoFactory.updateRoutineTodoRequestReadyDtoWithChangedHardness();

        try {
            mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routine.getId() +
                    API_BASE_TODOS + "/" + routineTodo.getId())
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
    public void deleteRoutineTodo_shouldDeleteRoutineTodo_whenIdIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        routineTodoRepository.save(routineTodo);

        mockMvc.perform(delete(buildDeleteRoutineTodoByIdUrl(routine.getId(), routineTodo.getId())))
                .andExpect(status().isOk());

        assertThat(routineTodoRepository.findById(routineTodo.getId())).isEmpty();
        assertThat(routineTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void deleteRoutineTodo_shouldDeleteRoutineTodoInstances_whenIdIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstanceWithRoutine(routine));
        routineTodoInstanceRepository.save(RoutineTodoInstanceFactory
                .routineTodoInstanceWithRoutineAndRoutineTodoInstance(routineInstance, routineTodo));

        mockMvc.perform(delete(buildDeleteRoutineTodoByIdUrl(routine.getId(), routineTodo.getId())))
                .andExpect(status().isOk());

        assertThat(routineTodoRepository.findById(routineTodo.getId())).isEmpty();
        assertThat(routineTodoRepository.count()).isEqualTo(0);
        assertThat(routineTodoInstanceRepository.count()).isEqualTo(0);
    }


    @Test
    public void findRoutineTodosByRoutineId_shouldReturnExistingRoutineTodo_whenIdIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
        routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));

        mockMvc.perform(get(buildGetRoutineTodosByDayIdUrl(routine.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }
}
