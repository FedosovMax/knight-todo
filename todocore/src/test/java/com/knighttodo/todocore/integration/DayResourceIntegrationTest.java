package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.factories.DayFactory;
import com.knighttodo.todocore.factories.DayTodoFactory;
import com.knighttodo.todocore.service.privatedb.repository.DayRepository;
import com.knighttodo.todocore.service.privatedb.repository.DayTodoRepository;
import com.knighttodo.todocore.service.privatedb.representation.Day;
import com.knighttodo.todocore.rest.request.DayRequestDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Date;

import static com.knighttodo.todocore.Constants.API_BASE_DAYS;
import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static com.knighttodo.todocore.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DayResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class DayResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private DayTodoRepository dayTodoRepository;

    @AfterEach
    public void tearDown() {
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
    void test() {
        assertTrue(postgresqlContainer.isRunning());
    }

    @Test
    public void addDay_shouldAddDayAndReturnIt_whenRequestIsCorrect() throws Exception {
        DayRequestDto requestDto = DayFactory.createDayRequestDto();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_DAYS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(dayRepository.count()).isEqualTo(1);
    }

    @Test
    public void addDay_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        DayRequestDto requestDto = DayFactory.createDayRequestDtoWithoutName();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_DAYS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayRepository.count()).isEqualTo(0);
    }

    @Test
    public void addDay_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        DayRequestDto requestDto = DayFactory.createDayRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(
                post(API_BASE_URL_V1 + API_BASE_DAYS)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(dayRepository.count()).isEqualTo(0);
    }

    @Test
    public void findAllDays_shouldReturnAllDays() throws Exception {
        dayRepository.save(DayFactory.dayInstance());
        dayRepository.save(DayFactory.dayInstance());

        mockMvc.perform(
                get(API_BASE_URL_V1 + API_BASE_DAYS))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void findDayById_shouldReturnExistingDay_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());

        mockMvc.perform(
                get(buildGetDayByIdUrl(day.getId())))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToId()).value(day.getId().toString()));
    }

    @Test
    public void updateDay_shouldUpdateDayAndReturnIt_whenRequestIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayRequestDto requestDto = DayFactory.updateDayRequestDto();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToDayName()).value(requestDto.getDayName()));
    }

    @Test
    public void updateDay_shouldRespondWithBadRequestStatus_whenDayNameIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayRequestDto requestDto = DayFactory.updateDayRequestDtoWithoutName();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDay_shouldRespondWithBadRequestStatus_whenDayNameConsistsOfSpaces() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        DayRequestDto requestDto = DayFactory
                .updateDayRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId())
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateDay_shouldUpdateDayNameAndCheckReturnFields_whenResponseIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        DayRequestDto dayRequestDto = DayFactory.createDayRequestDto();

        mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_DAYS + "/" + day.getId())
                .content(objectMapper.writeValueAsString(dayRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToDayName()).value(dayRequestDto.getDayName()))
                .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(dayRepository.count()).isEqualTo(1);
        assertThat(dayTodoRepository.count()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void deleteDay_shouldDeleteDay_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());

        mockMvc.perform(delete(buildDeleteDayByIdUrl(day.getId())))
                .andExpect(status().isOk());

        assertThat(dayRepository.findByIdAlive(day.getId())).isEmpty();
    }

    @Test
    @Transactional
    public void deleteDay_shouldDeleteDayWithAllDayTodos_whenIdIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));
        dayTodoRepository.save(DayTodoFactory.dayTodoWithDayInstance(day));

        mockMvc.perform(delete(buildDeleteDayByIdUrl(day.getId())))
                .andExpect(status().isOk());

        assertThat(dayRepository.findByIdAlive(day.getId())).isEmpty();
        assertThat(dayTodoRepository.findAllAlive().isEmpty());
    }

    @Test
    @Transactional
    public void findDayByDate_shouldReturnExistingDay_whenDateIsCorrect() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        LocalDate date = LocalDate.now();
        mockMvc.perform(get(buildGetDayByDate(date)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildJsonPathToDate()).value(day.getDate().toString()));
    }

    @Test
    public void findDayByDate_shouldRespondWithBadRequestStatus_whenWithoutDate() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        String date = "";
        mockMvc.perform(get(buildGetDayByDateWithoutDate(date)))
                        .andExpect(status().is5xxServerError());
    }

    @Test
    public void findDayByDate_shouldRespondWithBadRequestStatus_whenDateIsNull() throws Exception {
        Day day = dayRepository.save(DayFactory.dayInstance());
        String date = null;
        mockMvc.perform(get(buildGetDayByDateWithoutDate(date)))
                .andExpect(status().isBadRequest());
    }
}
