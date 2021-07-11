package com.knighttodo.knighttodo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.RoutineFactory;
import com.knighttodo.knighttodo.factories.RoutineInstanceFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
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

import java.util.List;
import java.util.UUID;

import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = RoutineResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class RoutineResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineInstanceRepository routineInstanceRepository;

    @AfterEach
    public void tearDown() {
        routineInstanceRepository.deleteAll();
        routineRepository.deleteAll();
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
    public void createRoutine_shouldAddRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        RoutineRequestDto requestDto = RoutineFactory.createRoutineRequestDto();

        mockMvc.perform(post(API_BASE_ROUTINES)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(buildJsonPathToName()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToHardness()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToScariness()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToReadyName()).value(false))
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
    }

    @Test
    public void createRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        RoutineRequestDto requestDto = RoutineFactory.createRoutineWithNullNameValueRequestDto();

        mockMvc.perform(post(API_BASE_ROUTINES)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(routineRepository.count()).isEqualTo(0);
    }

    @Test
    public void findAllRoutines_shouldReturnAllTodos() throws Exception {
        routineRepository.save(RoutineFactory.routineInstance());
        routineRepository.save(RoutineFactory.routineInstance());

        mockMvc.perform(get(API_BASE_ROUTINES))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void findRoutineById_shouldReturnExistingRoutine_whenIdIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());

        mockMvc.perform(get(buildGetRoutineByIdUrl(routine.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToId()).value(routine.getId()));
    }

    @Test
    public void updateRoutine_shouldUpdateRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDto();

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routine.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
                .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()))
                .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
                .andExpect(jsonPath(buildJsonPathToReadyName()).value(true))
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
        assertThat(routineRepository.findById(routine.getId()).get().getName()).isEqualTo(requestDto.getName());
    }

    @Test
    public void updateRoutine_shouldUpdateRoutineInstancesAndReturnUpdatedRoutine_whenRequestIsCorrect() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineInstance firstRoutineInstance = RoutineInstanceFactory.routineInstanceWithRoutine(routine);
        RoutineInstance secondRoutineInstance = RoutineInstanceFactory.routineInstanceWithRoutine(routine);
        RoutineInstance thirdRoutineInstance = RoutineInstanceFactory.routineInstanceWithRoutine(routine);
        RoutineInstance fourthRoutineInstance = RoutineInstanceFactory.routineInstanceWithRoutine(routine);

        routineInstanceRepository.saveAll(List.of(firstRoutineInstance, secondRoutineInstance, thirdRoutineInstance, fourthRoutineInstance));

        List<UUID> updatedRoutineTodoIds = List.of(firstRoutineInstance.getId(), secondRoutineInstance.getId(), thirdRoutineInstance.getId());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDtoWithInstanceIds(updatedRoutineTodoIds);

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routine.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToRoutineInstanceIdInInstancesListByIndex(0)).value(firstRoutineInstance.getId()))
                .andExpect(jsonPath(buildJsonPathToRoutineInstanceIdInInstancesListByIndex(1)).value(secondRoutineInstance.getId()))
                .andExpect(jsonPath(buildJsonPathToRoutineInstanceIdInInstancesListByIndex(2)).value(thirdRoutineInstance.getId()));

        assertThat(routineRepository.count()).isEqualTo(1);
        assertThat(routineInstanceRepository.count()).isEqualTo(4);
    }

    @Test
    public void updateRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineWithNullNaveValueRequestDto();

        mockMvc.perform(put(API_BASE_ROUTINES + "/" + routine.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRoutine_shouldDeleteRoutine_whenIdIsCorrect() throws Exception {
        Routine routine = RoutineFactory.routineInstance();
        Routine savedRoutine = routineRepository.save(routine);

        RoutineInstance routineInstance = RoutineInstanceFactory.routineInstanceWithRoutine(routine);
        routineInstanceRepository.save(routineInstance);

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(routine.getId())))
                .andExpect(status().isOk());

        assertThat(routineRepository.findById(routine.getId())).isEmpty();
        assertThat(routineRepository.count()).isEqualTo(0);
    }
}
