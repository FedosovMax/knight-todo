package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.factories.ReminderFactory;
import com.knighttodo.todocore.rest.request.CreateReminderRequestDto;
import com.knighttodo.todocore.rest.request.UpdateReminderRequestDto;
import com.knighttodo.todocore.service.privatedb.repository.ReminderRepository;
import com.knighttodo.todocore.service.privatedb.representation.Reminder;
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
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.knighttodo.todocore.Constants.API_BASE_REMINDER;
import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ReminderIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class ReminderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReminderRepository reminderRepository;

    @AfterEach
    public void tearDown() {
        reminderRepository.deleteAll();
    }

    @Test
    public void getReminderById_happyPath() throws Exception {
        Reminder savedReminder = saveReminder();

        getReminderById(savedReminder.getId()).andExpect(status().isOk());

        Reminder reminder = reminderRepository.findById(savedReminder.getId()).orElse(null);

        assertThat(reminder).isNotNull();
        assertThat(reminder.getId()).isEqualTo(reminder.getId());
        assertThat(reminder.getName()).isNotNull();
        assertThat(reminder.getMessage()).isNotNull();
        assertThat(reminder.getReminderDate()).isNotNull();
    }

    @Test
    public void getReminderById_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        saveReminder();
        getReminderById(UUID.randomUUID()).andExpect(status().isNotFound());
    }

    @Test
    public void deleteReminderById_happyPath() throws Exception {
        Reminder reminder = saveReminder();

        deleteReminderById(reminder.getId()).andExpect(status().isOk());
    }

    @Test
    public void deleteReminderById_whenNotFound_thenReturnNotFoundStatusCode() throws Exception {
        saveReminder();
        deleteReminderById(UUID.randomUUID()).andExpect(status().isNotFound());
    }

    @Test
    public void addReminder_whenCreateValidReminder_thenSaveSuccessfullyReminder() throws Exception {
        CreateReminderRequestDto requestDto = ReminderFactory.getCreateReminderRequestDto();

        createReminder(requestDto).andExpect(status().isCreated());

        List<Reminder> reminders = reminderRepository.findAll();

        assertThat(reminders.size()).isEqualTo(1);

        Reminder reminder = reminders.get(0);
        assertThat(reminder.getName()).isEqualTo("Name to create");
        assertThat(reminder.getMessage()).isEqualTo("Message to create");
        assertThat(reminder.getReminderDate()).isNotNull();
    }

    @Test
    public void addReminder_whenCreateReminderWithDateBeforeToday_thenSaveSuccessfullyReminder() throws Exception {
        CreateReminderRequestDto requestDto = ReminderFactory.getCreateReminderRequestDto();
        requestDto.setReminderDate(LocalDateTime.now().minusDays(1));

        createReminder(requestDto).andExpect(status().isConflict());
    }

    @Test
    public void updateReminder_whenUpdateValidReminder_thenUpdateSuccessfullyReminder() throws Exception {
        Reminder savedReminder = saveReminder();

        UpdateReminderRequestDto requestDto = ReminderFactory.getUpdateReminderRequestDto();

        updateReminder(requestDto, savedReminder.getId()).andExpect(status().isOk());

        Reminder reminder = reminderRepository.findById(savedReminder.getId()).orElse(null);

        assertThat(reminder).isNotNull();
        assertThat(reminder.getId()).isEqualTo(reminder.getId());
        assertThat(reminder.getName()).isEqualTo("Name to update");
        assertThat(reminder.getMessage()).isEqualTo("Message to update");
        assertThat(reminder.getReminderDate()).isNotNull();
    }

    @Test
    public void updateReminder_whenUpdateReminderWithDateBeforeToday_thenUpdateSuccessfullyReminder() throws Exception {
        Reminder savedReminder = saveReminder();

        UpdateReminderRequestDto requestDto = ReminderFactory.getUpdateReminderRequestDto();
        requestDto.setReminderDate(LocalDateTime.now().minusDays(1));

        updateReminder(requestDto, savedReminder.getId()).andExpect(status().isConflict());
    }

    private ResultActions deleteReminderById(UUID id) throws Exception {
        return mockMvc.perform(delete(API_BASE_URL_V1 + API_BASE_REMINDER + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private ResultActions getReminderById(UUID id) throws Exception {
        return mockMvc.perform(get(API_BASE_URL_V1 + API_BASE_REMINDER + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private ResultActions createReminder(CreateReminderRequestDto requestDto) throws Exception {
        return mockMvc.perform(post(API_BASE_URL_V1 + API_BASE_REMINDER)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private ResultActions updateReminder(UpdateReminderRequestDto requestDto, UUID reminderId) throws Exception {
        return mockMvc.perform(put(API_BASE_URL_V1 + API_BASE_REMINDER + "/" + reminderId)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    private Reminder saveReminder() {
        return reminderRepository.save(Reminder.builder()
                .name("old name")
                .message("old message")
                .reminderDate(LocalDateTime.now().plusDays(1))
                .build());
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
}
