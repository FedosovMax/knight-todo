package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutinesName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.RoutineFactory;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoBlockResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @Autowired
    private RoutineRepository routineRepository;

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
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(requestDto.getBlockName()));
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDtoWithoutName();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTodoBlock_shouldRespondWithBadRequestStatus_whenBlockNameConsistsOfSpaces() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory
            .updateTodoBlockRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteTodoBlock_shouldDeleteTodoBlock_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());

        mockMvc.perform(delete(buildDeleteBlockByIdUrl(todoBlock.getId())))
            .andExpect(status().isOk());

        assertThat(todoBlockRepository.findById(todoBlock.getId())).isEmpty();
    }

    @Test
    public void addTodoBlock_shouldAddTodoBlockWithRoutinesAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Routine routineFirst = routineRepository.save(RoutineFactory.createRoutineInstance());
        routineFirst.setTemplateId(routineFirst.getId());
        routineFirst = routineRepository.save(routineFirst);
        routineFirst.getTodos().add(TodoFactory.todoWithBlockIdInstance(todoBlock));
        routineFirst.getTodos().add(TodoFactory.todoWithBlockIdInstance(todoBlock));
        CreateTodoBlockRequestDto requestDto = TodoBlockFactory.createTodoBlockRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToRoutinesName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());
    }

    @Test
    public void updateTodoBlock_shouldUpdateTodoBlockWithRoutinesAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());

        Routine routineFirst = routineRepository.save(RoutineFactory.createRoutineInstance());
        routineFirst.setTemplateId(routineFirst.getId());
        routineRepository.save(routineFirst);

        UpdateTodoBlockRequestDto requestDto = TodoBlockFactory.updateTodoBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value("Friday Todos"))
            .andExpect(jsonPath(buildJsonPathToId()).exists());
    }
}
