package com.knighttodo.knighttodo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoBlockResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @Before
    public void setUp() {
        todoBlockRepository.deleteAll();
    }

    @Test
    public void addTodoBlock_shouldAddTodoBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoFactory.createTodoBlockRequestDto();

        mockMvc.perform(
            post("/blocks")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());

        assertThat(todoBlockRepository.count()).isEqualTo(1);
    }

    @Test
    public void addTodoBlock_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoFactory.createTodoBlockRequestDtoWithoutName();

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    private void expectBadRequestStatusResponseOnCreateRequest(CreateTodoBlockRequestDto requestDto) throws Exception {
        mockMvc.perform(
            post("/blocks")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoBlockRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodoBlock_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoFactory.createTodoBlockRequestDtoWithNameConsistingOfSpaces();

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void addTodoBlock_shouldRespondWithBadRequestStatus_whenTodosIsNull() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoFactory.createTodoBlockRequestDtoWithoutTodos();

        expectBadRequestStatusResponseOnCreateRequest(requestDto);
    }

    @Test
    public void getAllTodoBlocks_shouldReturnAllTodoBlocks() throws Exception {
        todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        todoBlockRepository.save(TodoFactory.notSavedTodoBlock());

        mockMvc.perform(
            get("/blocks"))
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getTodoBlockById_shouldReturnExistingTodoBlock_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());

        mockMvc.perform(
            get("/blocks/" + todoBlock.getId()))
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.id").value(todoBlock.getId()));
    }

    @Test
    public void updateTodoBlock_shouldUpdateTodoBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        UpdateTodoBlockRequestDto requestDto = TodoFactory.updateTodoBlockRequestDto(todoBlock);

        mockMvc.perform(
            put("/blocks")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.blockName").value(requestDto.getBlockName()))
            .andExpect(jsonPath("$.todos.length()").value(requestDto.getTodos().size()));

        assertThat(todoBlockRepository.findById(todoBlock.getId()).get().getBlockName())
            .isEqualTo(requestDto.getBlockName());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenIdIsIncorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        UpdateTodoBlockRequestDto requestDto = TodoFactory.updateTodoBlockRequestDtoWithIncorrectId(todoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
    }

    private void expectBadRequestStatusResponseOnUpdateRequest(UpdateTodoBlockRequestDto requestDto) throws Exception {
        mockMvc.perform(
            put("/todos")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    private void expectThatTodoBlockWasNotUpdated(UpdateTodoBlockRequestDto requestDto) {
        assertThat(todoBlockRepository.findById(requestDto.getId()).get().getBlockName())
            .isNotEqualTo(requestDto.getBlockName());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        UpdateTodoBlockRequestDto requestDto = TodoFactory.updateTodoBlockRequestDtoWithoutName(todoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoBlockWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        UpdateTodoBlockRequestDto requestDto = TodoFactory
            .updateTodoBlockRequestDtoWithNameConsistingOfSpaces(todoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoBlockWasNotUpdated(requestDto);
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenTodosIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());
        UpdateTodoBlockRequestDto requestDto = TodoFactory.updateTodoBlockRequestDtoWithoutTodos(todoBlock);

        expectBadRequestStatusResponseOnUpdateRequest(requestDto);
        expectThatTodoBlockWasNotUpdated(requestDto);
    }

    @Test
    public void deleteTodoBlock_shouldDeleteTodoBlock_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoFactory.notSavedTodoBlock());

        mockMvc.perform(
            delete("/blocks/" + todoBlock.getId()))
            .andExpect(status().isOk());

        assertThat(todoBlockRepository.findById(todoBlock.getId())).isEmpty();
    }
}
