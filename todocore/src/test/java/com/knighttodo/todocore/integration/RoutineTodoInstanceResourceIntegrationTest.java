//package com.knighttodo.todocore.integration;
//
//import com.knighttodo.todocore.factories.RoutineFactory;
//import com.knighttodo.todocore.factories.RoutineInstanceFactory;
//import com.knighttodo.todocore.factories.RoutineTodoFactory;
//import com.knighttodo.todocore.factories.RoutineTodoInstanceFactory;
//import com.knighttodo.todocore.gateway.character.response.ExperienceResponse;
//import com.knighttodo.todocore.service.privatedb.repository.RoutineInstanceRepository;
//import com.knighttodo.todocore.service.privatedb.repository.RoutineRepository;
//import com.knighttodo.todocore.service.privatedb.repository.RoutineTodoInstanceRepository;
//import com.knighttodo.todocore.service.privatedb.repository.RoutineTodoRepository;
//import com.knighttodo.todocore.service.privatedb.representation.Routine;
//import com.knighttodo.todocore.service.privatedb.representation.RoutineInstance;
//import com.knighttodo.todocore.service.privatedb.representation.RoutineTodo;
//import com.knighttodo.todocore.service.privatedb.representation.RoutineTodoInstance;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.support.TestPropertySourceUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static com.knighttodo.todocore.Constants.*;
//import static com.knighttodo.todocore.TestConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(initializers = RoutineTodoInstanceResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
//@Testcontainers
//public class RoutineTodoInstanceResourceIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private RoutineTodoRepository routineTodoRepository;
//
//    @Autowired
//    private RoutineTodoInstanceRepository routineTodoInstanceRepository;
//
//    @Autowired
//    private RoutineInstanceRepository routineInstanceRepository;
//
//    @Autowired
//    private RoutineRepository routineRepository;
//
//    @MockBean
//    private RestTemplate restTemplate;
//
//    @AfterEach
//    public void tearDown() {
//        routineTodoInstanceRepository.deleteAll();
//        routineInstanceRepository.deleteAll();
//        routineTodoRepository.deleteAll();
//        routineRepository.deleteAll();
//    }
//
//    @Container
//    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:11.1");
//
//    static {
//        postgresqlContainer.start();
//    }
//
//    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        @Override
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//
//            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
//                    applicationContext,
//                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
//                    "spring.datasource.password=" + postgresqlContainer.getPassword()
//            );
//        }
//    }
//
//    @Test
//    public void findAllRoutineInstanceTodos_shouldReturnAllRoutineTodoInstances() throws Exception {
//        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
//
//        routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.routineTodoInstanceWithRoutineInstance(routineInstance));
//        routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.routineTodoInstanceWithRoutineInstance(routineInstance));
//
//        mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/" + routineInstance.getId() + API_BASE_ROUTINES_TODO_INSTANCES))
//                .andExpect(status().isFound())
//                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
//    }
//
//    @Test
//    public void findRoutineTodoInstanceById_shouldReturnExistingRoutineTodoInstance_whenIdIsCorrect() throws Exception {
//        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
//        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstanceWithRoutine(routine));
//        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
//        RoutineTodoInstance routineTodoInstance = RoutineTodoInstanceFactory.routineTodoInstanceWithRoutineInstance(routineInstance);
//        routineTodoInstance.setRoutineTodo(routineTodo);
//        RoutineTodoInstance savedRoutineTodoInstance = routineTodoInstanceRepository.save(routineTodoInstance);
//
//        mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/" + routineInstance.getId() +
//                API_BASE_ROUTINES_TODO_INSTANCES + "/" + savedRoutineTodoInstance.getId()))
//                .andExpect(status().isFound())
//                .andExpect(jsonPath(buildJsonPathToId()).value(savedRoutineTodoInstance.getId().toString()));
//    }
//
//    @Test
//    public void findRoutineTodoInstancesByRoutineInstanceId_shouldReturnExistingRoutineTodoInstances_whenIdIsCorrect() throws Exception {
//        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstance());
//        routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.routineTodoInstanceWithRoutineInstance(routineInstance));
//        routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.routineTodoInstanceWithRoutineInstance(routineInstance));
//
//        mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/" + routineInstance.getId() + API_BASE_ROUTINES_TODO_INSTANCES))
//                .andExpect(status().isFound())
//                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
//    }
//
//    @Test
//    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyTrue_whenRoutineTodoInstanceIdIsCorrect() throws Exception {
//        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
//        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstanceWithRoutine(routine));
//        RoutineTodoInstance savedRoutineTodoInstance = routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.
//                routineTodoInstanceWithRoutineInstance(routineInstance));
//        ExperienceResponse experienceResponse = RoutineTodoInstanceFactory.experienceResponseInstance(savedRoutineTodoInstance.getId());
//
//        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
//                .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));
//
//        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/" + routineInstance.getId() +
//                API_BASE_ROUTINES_TODO_INSTANCES + "/" + savedRoutineTodoInstance.getId() + BASE_READY)
//                .param(PARAM_READY, PARAMETER_TRUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath(buildJsonPathToRoutineId()).isNotEmpty())
//                .andExpect(jsonPath(buildJsonPathToExperience()).isNotEmpty())
//                .andExpect(jsonPath(buildJsonPathToReadyName()).value(true));
//
//        assertThat(routineTodoInstanceRepository.findByIdAlive(savedRoutineTodoInstance.getId()).get().isReady()).isEqualTo(true);
//    }
//
//    @Test
//    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyFalse_whenRoutineTodoIdIsCorrect() throws Exception {
//        Routine routine = RoutineFactory.routineInstance();
//        Routine savedRoutine = routineRepository.save(routine);
//        RoutineTodo routineTodo = routineTodoRepository.save(RoutineTodoFactory.routineTodoWithRoutine(routine));
//        routine.setRoutineTodos(List.of(routineTodo));
//
//        RoutineInstance routineInstance = routineInstanceRepository.save(RoutineInstanceFactory.routineInstanceWithRoutine(savedRoutine));
//        RoutineTodoInstance savedRoutineTodoInstanceReadyTrue = routineTodoInstanceRepository.save(RoutineTodoInstanceFactory.
//                routineTodoInstanceWithRoutineReadyInstance(routineInstance, routineTodo));
//        ExperienceResponse experienceResponse = RoutineTodoInstanceFactory.experienceResponseInstance(savedRoutineTodoInstanceReadyTrue.getId());
//
//        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
//                .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));
//
//        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/" + routineInstance.getId() +
//                API_BASE_ROUTINES_TODO_INSTANCES + "/" + savedRoutineTodoInstanceReadyTrue.getId() + BASE_READY)
//                .param(PARAM_READY, PARAMETER_FALSE))
//                .andExpect(status().isOk());
//
//        assertThat(routineTodoInstanceRepository.findByIdAlive(savedRoutineTodoInstanceReadyTrue.getId()).get().isReady()).isEqualTo(false);
//    }
//}
