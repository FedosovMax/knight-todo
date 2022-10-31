package com.knighttodo.todocore.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.todocore.Constants;
import com.knighttodo.todocore.factories.UserFactory;
import com.knighttodo.todocore.service.privatedb.repository.RoleRepository;
import com.knighttodo.todocore.service.privatedb.repository.UserRepository;
import com.knighttodo.todocore.service.privatedb.representation.User;
import com.knighttodo.todocore.rest.request.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
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

import javax.transaction.Transactional;

import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static com.knighttodo.todocore.Constants.USERS_BASE_URL;
import static com.knighttodo.todocore.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = UserResourceIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public class UserResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
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
    public void test() {
        assertTrue(postgresqlContainer.isRunning());
    }

//    @Test
//    public void addUser_shouldAddUserAndReturnIt_whenRequestIsCorrect() throws Exception {
//        UserRequestDto userRequestDto = UserFactory.createUserRequestDtoInstance();
//        roleRepository.save(Role.builder().name("ROLE_USER").build());
//
//        mockMvc.perform(post(API_BASE_URL_V1 + USERS_BASE_URL)
//                        .content(objectMapper.writeValueAsString(userRequestDto))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath(buildLoginJsonPath()).exists());
//
//        assertThat(userRepository.count()).isEqualTo(1);
//    }

    @Test
    public void addUser_shouldRespondWithBadRequestStatus_whenLoginIsNull() throws Exception {
        UserRequestDto requestDto = UserFactory.createUserRequestDtoWithoutLoginInstance();

        mockMvc.perform(post(API_BASE_URL_V1 + USERS_BASE_URL)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    public void addUser_shouldRespondWithBadRequestStatus_whenLoginConsistsOfSpaces() throws Exception {
        UserRequestDto requestDto = UserFactory.createUserRequestDtoWithEmptyLoginInstance();

        mockMvc.perform(post(API_BASE_URL_V1 + USERS_BASE_URL)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllUsers_shouldReturnAllUsers() throws Exception {
        userRepository.save(UserFactory.createUserInstance());
        userRepository.save(UserFactory.createUserInstance());

        mockMvc.perform(MockMvcRequestBuilders.get(API_BASE_URL_V1 + USERS_BASE_URL))
                .andExpect(status().isFound())
                .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

//    @Test
//    public void getUserById_shouldReturnExistingUser_whenIdIsCorrect() throws Exception {
//        User user = userRepository.save(UserFactory.createUserInstance());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(Constants.buildGetUserByIdBaseUrl(user.getId())))
//                .andExpect(status().isFound())
//                .andExpect(jsonPath(buildIdJsonPath()).value(user.getId()))
//                .andExpect(jsonPath(buildLoginJsonPath()).value(user.getLogin()));
//    }

    @Test
    public void updateUser_shouldUpdateUserAndReturnIt_whenRequestIsCorrect() throws Exception {
        User user = userRepository.save(UserFactory.createUserInstance());
        UserRequestDto userRequestDto = UserFactory.updateUserRequestDtoInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(Constants.buildUpdateUserBaseUrl(user.getId()))
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(buildLoginJsonPath()).value(userRequestDto.getLogin()));

        assertThat(userRepository.findById(user.getId()).get().getLogin())
                .isEqualTo(userRequestDto.getLogin());
    }

    @Test
    public void updateUser_shouldRespondWithBadRequestStatus_whenLoginIsNull() throws Exception {
        User user = userRepository.save(UserFactory.createUserInstance());
        UserRequestDto userRequestDto = UserFactory.updateUserRequestDtoWithoutLoginInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(Constants.buildUpdateUserBaseUrl(user.getId()))
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser_shouldRespondWithBadRequestStatus_whenLoginConsistsOfSpaces() throws Exception {
        User user = userRepository.save(UserFactory.createUserInstance());
        UserRequestDto userRequestDto = UserFactory.updateUserRequestDtoWithLoginConsistingSpacesInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(Constants.buildUpdateUserBaseUrl(user.getId()))
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser_shouldRespondWithBadRequestStatus_whenPasswordIsNull() throws Exception {
        User user = userRepository.save(UserFactory.createUserInstance());
        UserRequestDto userRequestDto = UserFactory.updateUserRequestDtoWithoutPasswordInstance();

        mockMvc.perform(MockMvcRequestBuilders.put(Constants.buildUpdateUserBaseUrl(user.getId()))
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteUser_shouldDeleteUser_whenIdIsCorrect() throws Exception {
        User user = userRepository.save(UserFactory.createUserInstance());

        mockMvc.perform(MockMvcRequestBuilders.delete(Constants.buildDeleteUserByIdUrl(user.getId())))
                .andExpect(status().isOk());

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
