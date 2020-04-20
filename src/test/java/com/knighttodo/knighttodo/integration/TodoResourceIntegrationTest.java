package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.PARAM_READY;
import static com.knighttodo.knighttodo.TestConstants.PARAMETER_FALSE;
import static com.knighttodo.knighttodo.TestConstants.PARAMETER_TRUE;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteTodoByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetTodoByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetTodosByBlockIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToExperience;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToHardness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToReadyName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToScariness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoBlockId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoName;
import static com.knighttodo.knighttodo.TestConstants.buildUpdateTodoReadyBaseUrl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${baseUrl.experience}")
    private String experienceUrl;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    public void addTodo_shouldAddTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDto(todoBlock);

        mockMvc.perform(
            post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToTodoBlockId()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(todoRepository.count()).isEqualTo(1);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutName(todoBlock);

        mockMvc.perform(
            post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateTodoRequestDto requestDto = TodoFactory
            .createTodoRequestDtoWithNameConsistingOfSpaces(todoBlock);

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateTodoRequestDto requestDto = TodoFactory
            .createTodoRequestDtoWithoutScariness(todoBlock);

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutHardness(todoBlock);

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllTodos_shouldReturnAllTodos() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));

        mockMvc.perform(get(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getTodoById_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));

        mockMvc.perform(get(buildGetTodoByIdUrl(todoBlock.getId(), todo.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(todo.getId()));
    }

    @Test
    public void updateTodo_shouldUpdateTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDto(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoName()).value(requestDto.getTodoName()))
            .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()));

        assertThat(todoRepository.findById(todo.getId()).get().getTodoName()).isEqualTo(requestDto.getTodoName());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutName(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory
            .updateTodoRequestDtoWithNameConsistingOfSpaces(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenScarinessIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutScariness(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutHardness(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodo_shouldUpdateReadyTodoAndReturnIt_whenScarinessAndHardnessAreUnchanged() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdReadyInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestReadyDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
                            .content(objectMapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoName()).value(requestDto.getTodoName()))
            .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()));

        assertThat(todoRepository.findById(todo.getId()).get().getScariness()).isEqualTo(requestDto.getScariness());
        assertThat(todoRepository.findById(todo.getId()).get().getHardness()).isEqualTo(requestDto.getHardness());
    }

    @Test
    public void updateTodo_shouldThrowUnchangeableFieldUpdateException_whenTodoWasReadyAndScarinessWasChanged() {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdReadyInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestReadyDtoWithChangedScariness();

        try {
            mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception was't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(todoRepository.findById(todo.getId()).get().getScariness()).isEqualTo(todo.getScariness());
    }

    @Test
    public void updateTodo_shouldThrowUnchangeableFieldUpdateException_whenTodoWasReadyAndHardnessWasChanged() {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdReadyInstance(todoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestReadyDtoWithChangedHardness();

        try {
            mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_TODOS + "/" + todo.getId())
                                .content(objectMapper.writeValueAsString(requestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE));
            fail("Exception was't thrown");
        } catch (Exception e) {
            assertEquals(UnchangeableFieldUpdateException.class, e.getCause().getClass());
            assertEquals("Can not update todo's field in ready state", e.getCause().getMessage());
        }
        assertThat(todoRepository.findById(todo.getId()).get().getHardness()).isEqualTo(todo.getHardness());
    }

    @Test
    @Transactional
    public void deleteTodo_shouldDeleteTodo_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));

        mockMvc.perform(delete(buildDeleteTodoByIdUrl(todoBlock.getId(), todo.getId())))
            .andExpect(status().isOk());

        assertThat(todoRepository.findById(todo.getId())).isEmpty();
    }

    @Test
    public void getTodosByBlockId_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo firstTodo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));

        mockMvc.perform(get(buildGetTodosByBlockIdUrl(todoBlock.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyTrue_whenTodoIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todo = todoRepository.save(TodoFactory.todoWithBlockIdInstance(todoBlock));
        ExperienceResponse experienceResponse = TodoFactory.experienceResponseInstance(todo.getId());

        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
            .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));

        mockMvc.perform(put(buildUpdateTodoReadyBaseUrl(todoBlock, todo))
            .param(PARAM_READY, PARAMETER_TRUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoBlockId()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToExperience()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(true));

        assertThat(todoRepository.findById(todo.getId()).get().isReady()).isEqualTo(true);
    }

    @Test
    public void updateIsReady_shouldReturnOk_shouldMakeIsReadyFalse_whenTodoIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Todo todoWithReadyTrue = todoRepository.save(TodoFactory.todoWithBlockIdReadyInstance(todoBlock));
        ExperienceResponse experienceResponse = TodoFactory.experienceResponseInstance(todoWithReadyTrue.getId());

        when(restTemplate.postForEntity(anyString(), any(), eq(ExperienceResponse.class)))
            .thenReturn(new ResponseEntity<>(experienceResponse, HttpStatus.OK));

        mockMvc.perform(put(buildUpdateTodoReadyBaseUrl(todoBlock, todoWithReadyTrue))
            .param(PARAM_READY, PARAMETER_FALSE))
            .andExpect(status().isOk());

        assertThat(todoRepository.findById(todoWithReadyTrue.getId()).get().isReady()).isEqualTo(false);
    }
}
