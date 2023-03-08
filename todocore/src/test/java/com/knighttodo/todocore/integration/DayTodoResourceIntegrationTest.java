package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.exception.UnchangeableFieldUpdateException;
import com.knighttodo.todocore.factories.DayFactory;
import com.knighttodo.todocore.factories.DayTodoFactory;
import com.knighttodo.todocore.service.privatedb.repository.DayRepository;
import com.knighttodo.todocore.service.privatedb.repository.DayTodoRepository;
import com.knighttodo.todocore.service.privatedb.representation.Day;
import com.knighttodo.todocore.service.privatedb.representation.DayTodo;
import com.knighttodo.todocore.rest.request.DayTodoRequestDto;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.knighttodo.todocore.Constants.*;
import static com.knighttodo.todocore.TestConstants.*;
import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DayTodoResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class DayTodoResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DayTodoRepository dayTodoRepository;

    @Autowired
    private DayRepository dayRepository;

    @MockBean
    private RestTemplate restTemplate;

//    @Value("${baseUrl.experience}")
//    private String experienceUrl;

    @AfterEach
    public void tearDown() {
        dayTodoRepository.deleteAll();
        dayRepository.deleteAll();
    }

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13.2");

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
    public void addDayTodo_shouldAddDayTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());

        DayTodoRequestDto requestDto = DayTodoFactory.createDayTodoRequestDto();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(buildJsonPathToDayId()).isNotEmpty())
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(dayTodoRepository.count()).isEqualTo(1);
    }

    @Test
    public void addDayTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodoRequestDto requestDto = DayTodoFactory.createDayTodoRequestDtoWithoutName();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addDayTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodoRequestDto requestDto = DayTodoFactory.createDayTodoRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addDayTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodoRequestDto requestDto = DayTodoFactory.createDayTodoRequestDtoWithoutScariness();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addDayTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodoRequestDto requestDto = DayTodoFactory.createDayTodoRequestDtoWithoutHardness();

        mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayTodoRepository.count()).isEqualTo(0);
    }

    @Test
    public void findAllDayTodos_shouldReturnAllTodos() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void findDayTodoById_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        mockMvc.perform(get(buildGetDayTodoByIdUrl(day.getId(), dayTodo.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToId()).value(dayTodo.getId().toString()));
    }

    @Test
    public void updateDayTodo_shouldUpdateTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestDto(day);

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToTodoName()).value(requestDto.getDayTodoName()))
                .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
                .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()))
                .andExpect(jsonPath(buildJsonPathToOrderNumber()).value(requestDto.getOrderNumber()))
                .andExpect(jsonPath(buildJsonPathToColor()).value(requestDto.getColor()));

        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId()).get().getDayTodoName()).isEqualTo(requestDto.getDayTodoName());
    }

    @Test
    public void updateDayTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestDtoWithoutName(day);

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDayTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory
                .updateDayTodoRequestDtoWithNameConsistingOfSpaces(day);

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDayTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestDtoWithoutScariness(day);

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDayTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestDtoWithoutHardness(day);

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDayTodo_shouldUpdateReadyTodoAndReturnIt_whenScarinessAndHardnessAreUnchanged() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayReadyInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestReadyDto();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToTodoName()).value(requestDto.getDayTodoName()))
                .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
                .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()));

        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId()).get().getScariness()).isEqualTo(requestDto.getScariness());
        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId()).get().getHardness()).isEqualTo(requestDto.getHardness());
    }

    @Test
    public void updateDayTodo_shouldThrowUnchangeableFieldUpdateException_whenTodoWasReadyAndScarinessWasChanged() {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayReadyInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestReadyDtoWithChangedScariness();

        try {
            mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update day todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId()).get().getScariness()).isEqualTo(dayTodo.getScariness());
    }

    @Test
    public void updateDayTodo_shouldThrowUnchangeableFieldUpdateException_whenTodoWasReadyAndHardnessWasChanged() {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayReadyInstance(day));
        DayTodoRequestDto requestDto = DayTodoFactory.updateDayTodoRequestReadyDtoWithChangedHardness();

        try {
            mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS + "/" + dayTodo.getId())
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update day todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId()).get().getHardness()).isEqualTo(dayTodo.getHardness());
    }

    @Test
    @Transactional
    public void deleteDayTodo_shouldDeleteTodo_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo dayTodo = DayTodoFactory.dayTodoWithDayInstance(day);
        dayTodo.setDay(day);
        dayTodoRepository.save(dayTodo);

        mockMvc.perform(delete(buildDeleteTodoByIdUrl(day.getId(), dayTodo.getId())))
                .andExpect(status().isOk());

        assertThat(dayTodoRepository.findByIdAlive(dayTodo.getId())).isEmpty();
        assertThat(dayTodoRepository.count()).isEqualTo(1);
    }

    @Test
    public void findDayTodosByDayId_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo firstDayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        mockMvc.perform(get(buildGetTodosByDayIdUrl(day.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }
    @Test
    public void updateOrderNumberByDayTodoId_shouldReturnUpdatedTodo_whenAllDayIdTodoExist() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayTodo firstDayTodo = dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        Map<UUID, Integer> map = new HashMap<>();
        map.put(firstDayTodo.getId(), 777);

        mockMvc.perform(patch(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(map))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathForExtractOrderNumber(0)).value(777));

        assertThat(dayTodoRepository.findById(firstDayTodo.getId()).get().getOrderNumber()).isEqualTo(777);
    }
}

