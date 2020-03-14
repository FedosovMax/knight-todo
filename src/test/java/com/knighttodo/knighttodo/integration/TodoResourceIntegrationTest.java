package com.knighttodo.knighttodo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.knighttodo.knighttodo.TestConstants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.TestConstants.API_GET_TODOS_BY_PATH_VARIABLE_BLOCK_ID;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_HARDNESS;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_ID;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_LENGTH;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_SCARYNESS;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_TODO_BLOCK_ID;
import static com.knighttodo.knighttodo.TestConstants.JSON_PATH_TODO_NAME;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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

    private TodoBlock savedTodoBlock;

    private TodoBlock savedUpdatedTodoBlock;

    @Before
    public void setUp() {
        todoRepository.deleteAll();
        savedTodoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        savedUpdatedTodoBlock = todoBlockRepository.save(TodoFactory.notSavedUpdatedTodoBlock());
    }

    @Test
    public void addTodo_shouldAddTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDto(savedTodoBlock);

        mockMvc.perform(
            post(API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(JSON_PATH_ID).exists());

        assertThat(todoRepository.count()).isEqualTo(1);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutName(savedTodoBlock);

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    private void expectBadRequestStatusResponseOnCreateRequest(CreateTodoRequestDto requestDto) throws Exception {
        mockMvc.perform(
            post(API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithNameConsistingOfSpaces(savedTodoBlock);

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenScarynessIsNull() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutScaryness(savedTodoBlock);

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutHardness(savedTodoBlock);

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void addTodo_shouldRespondWithBadRequestStatus_whenTodoBlockIsNull() throws Exception {
        CreateTodoRequestDto requestDto = TodoFactory.createTodoRequestDtoWithoutTodoBlock();

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void getAllTodos_shouldReturnAllTodos() throws Exception {
        todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));

        mockMvc.perform(
            get(API_BASE_TODOS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(JSON_PATH_LENGTH).value(2));
    }

    @Test
    public void getTodoById_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));

        mockMvc.perform(
            get(API_BASE_TODOS + todo.getId()))
            .andExpect(status().isFound())
            .andExpect(jsonPath(JSON_PATH_ID).value(todo.getId()));
    }

    @Test
    public void updateTodo_shouldUpdateTodoAndReturnIt_whenRequestIsCorrect() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDto(todo, savedUpdatedTodoBlock);

        mockMvc.perform(
            put(API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(JSON_PATH_TODO_NAME).value(requestDto.getTodoName()))
            .andExpect(jsonPath(JSON_PATH_SCARYNESS).value(requestDto.getScaryness().getText()))
            .andExpect(jsonPath(JSON_PATH_HARDNESS).value(requestDto.getHardness().getText()))
            .andExpect(jsonPath(JSON_PATH_TODO_BLOCK_ID).value(requestDto.getTodoBlock().getId()));

        assertThat(todoRepository.findById(todo.getId()).get().getTodoName()).isEqualTo(requestDto.getTodoName());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenIdIsIncorrect() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithIncorrectId(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
    }

    private void expectBadRequestStatusResponseOnUpdateRequest(UpdateTodoRequestDto requestDto) throws Exception {
        mockMvc.perform(
            put(API_BASE_TODOS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    private void expectThatTodoWasNotUpdated(UpdateTodoRequestDto requestDto) {
        assertThat(todoRepository.findById(requestDto.getId()).get().getTodoName())
            .isNotEqualTo(requestDto.getTodoName());
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutName(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory
            .updateTodoRequestDtoWithNameConsistingOfSpaces(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenScarynessIsNull() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutScaryness(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenHardnessIsNull() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutHardness(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodo_shouldRespondWithBadRequestStatus_whenTodoBlockIsNull() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        UpdateTodoRequestDto requestDto = TodoFactory.updateTodoRequestDtoWithoutTodoBlock(todo, savedUpdatedTodoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoWasNotUpdated(requestDto);
    }

    @Test
    public void deleteTodo_shouldDeleteTodo_whenIdIsCorrect() throws Exception {
        Todo todo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));

        mockMvc.perform(
            delete(API_BASE_TODOS + todo.getId()))
            .andExpect(status().isOk());

        assertThat(todoRepository.findById(todo.getId())).isEmpty();
    }

    @Test
    public void getTodosByBlockId_shouldReturnExistingTodo_whenIdIsCorrect() throws Exception {
        Todo firstTodo = todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));
        todoRepository.save(TodoFactory.notSavedTodo(savedTodoBlock));

        mockMvc.perform(
            get(API_GET_TODOS_BY_PATH_VARIABLE_BLOCK_ID + firstTodo.getTodoBlock().getId()))
            .andExpect(status().isFound())
            .andExpect(jsonPath(JSON_PATH_LENGTH).value(2));
    }
}
