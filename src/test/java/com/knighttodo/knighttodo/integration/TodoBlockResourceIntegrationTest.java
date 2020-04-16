package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoBlockResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @BeforeEach
    public void setUp() {
        todoBlockRepository.deleteAll();
    }

    @Test
    public void addTodoBlock_shouldAddTodoBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoBlockFactory.createTodoBlockRequestDto();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(todoBlockRepository.count()).isEqualTo(1);
    }

    @Test
    public void addTodoBlock_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoBlockFactory.createTodoBlockRequestDtoWithoutName();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoBlockRepository.count()).isEqualTo(0);
    }

    @Test
    public void addTodoBlock_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        CreateTodoBlockRequestDto requestDto = TodoBlockFactory.createTodoBlockRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(todoBlockRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllTodoBlocks_shouldReturnAllTodoBlocks() throws Exception {
        todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());

        mockMvc.perform(
            get(API_BASE_BLOCKS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getTodoBlockById_shouldReturnExistingTodoBlock_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());

        mockMvc.perform(
            get(buildGetBlockByIdUrl(todoBlock.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(todoBlock.getId()));
    }

    @Test
    public void updateTodoBlock_shouldUpdateTodoBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDto(todoBlock);

        mockMvc.perform(
            put(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(requestDto.getBlockName()));

        assertThat(todoBlockRepository.findById(todoBlock.getId()).get().getBlockName())
            .isEqualTo(requestDto.getBlockName());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenIdIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDtoWithoutId(todoBlock);

        mockMvc.perform(put(API_BASE_BLOCKS)
                            .content(objectMapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenIdConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory
            .updateTodoBlockRequestDtoWithIdConsistingOfSpaces(todoBlock);

        mockMvc.perform(
            put(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDtoWithoutName(todoBlock);

        mockMvc.perform(
            put(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        assertThat(todoBlockRepository.findById(requestDto.getId()).get().getBlockName())
            .isNotEqualTo(requestDto.getBlockName());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory
            .updateTodoBlockRequestDtoWithNameConsistingOfSpaces(todoBlock);

        mockMvc.perform(
            put(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
        assertThat(todoBlockRepository.findById(requestDto.getId()).get().getBlockName())
            .isNotEqualTo(requestDto.getBlockName());
    }

    @Test
    @Transactional
    public void deleteTodoBlock_shouldDeleteTodoBlock_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());

        mockMvc.perform(
            delete(buildDeleteBlockByIdUrl(todoBlock.getId())))
            .andExpect(status().isOk());

        assertThat(todoBlockRepository.findById(todoBlock.getId())).isEmpty();
    }
}
