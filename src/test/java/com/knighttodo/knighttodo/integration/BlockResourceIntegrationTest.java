package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetBlockByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineHardnessInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineIdInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineNameInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineReadyInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineScarinessInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutinesName;

import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoIdInTodosListInRoutinesListByIndexes;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.knighttodo.knighttodo.factories.RoutineFactory;
import com.knighttodo.knighttodo.factories.BlockFactory;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.BlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.BlockRequestDto;

import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import java.util.List;
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
public class BlockResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        blockRepository.deleteAll();
    }

    @Test
    public void addBlock_shouldAddBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        BlockRequestDto requestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(blockRepository.count()).isEqualTo(1);
    }

    @Test
    public void addBlock_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        BlockRequestDto requestDto = BlockFactory.createBlockRequestDtoWithoutName();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(blockRepository.count()).isEqualTo(0);
    }

    @Test
    public void addBlock_shouldRespondWithBadRequestStatus_whenNameConsistsOfSpaces() throws Exception {
        BlockRequestDto requestDto = BlockFactory.createBlockRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(
            post(API_BASE_BLOCKS)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(blockRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllBlocks_shouldReturnAllBlocks() throws Exception {
        blockRepository.save(BlockFactory.BlockInstance());
        blockRepository.save(BlockFactory.BlockInstance());

        mockMvc.perform(
            get(API_BASE_BLOCKS))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getBlockById_shouldReturnExistingBlock_whenIdIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());

        mockMvc.perform(
            get(buildGetBlockByIdUrl(block.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(block.getId()));
    }

    @Test
    public void updateBlock_shouldUpdateBlockAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        BlockRequestDto requestDto = BlockFactory.updateBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(requestDto.getBlockName()));
    }

    @Test
    public void updateBlock_shouldRespondWithBadRequestStatus_whenBlockNameIsNull() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        BlockRequestDto requestDto = BlockFactory.updateBlockRequestDtoWithoutName();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBlock_shouldRespondWithBadRequestStatus_whenBlockNameConsistsOfSpaces() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        BlockRequestDto requestDto = BlockFactory
            .updateBlockRequestDtoWithNameConsistingOfSpaces();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBlock_shouldUpdateBlockNameAndCheckReturnFields_whenResponseIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());

        Routine routine = RoutineFactory.routineInstance();
        Routine savedRoutine = routineRepository.save(routine);
        savedRoutine.setBlock(block);
        savedRoutine.setTemplateId(savedRoutine.getId());
        routineRepository.save(savedRoutine);

        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        firstTodo.setBlock(block);
        firstTodo.setRoutine(routine);
        todoRepository.save(firstTodo);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        secondTodo.setBlock(block);
        todoRepository.save(secondTodo);

        List<String> updatedRoutineTodoIds = List.of(firstTodo.getId());
        RoutineRequestDto routineRequestDto = RoutineFactory.updateRoutineRequestDtoWithTodoIds(updatedRoutineTodoIds);
        BlockRequestDto blockRequestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId())
            .content(objectMapper.writeValueAsString(blockRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(blockRequestDto.getBlockName()))
            .andExpect(jsonPath(buildJsonPathToRoutineIdInRoutinesListByIndex(0)).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToRoutineNameInRoutinesListByIndex(0)).value(routineRequestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToRoutineHardnessInRoutinesListByIndex(0))
                .value(routineRequestDto.getHardness().toString()))
            .andExpect(jsonPath(buildJsonPathToRoutineScarinessInRoutinesListByIndex(0))
                .value(routineRequestDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToRoutineReadyInRoutinesListByIndex(0)).value(false))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListInRoutinesListByIndexes(0, 0)).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(blockRepository.count()).isEqualTo(1);
        assertThat(todoRepository.count()).isEqualTo(2);
        assertThat(routineRepository.count()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void deleteBlock_shouldDeleteBlock_whenIdIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());

        mockMvc.perform(delete(buildDeleteBlockByIdUrl(block.getId())))
            .andExpect(status().isOk());

        assertThat(blockRepository.findById(block.getId())).isEmpty();
    }

    @Test
    public void addBlock_shouldAddBlockWithRoutinesAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine firstRoutine = routineRepository.save(RoutineFactory.routineInstance());
        firstRoutine.setTemplateId(firstRoutine.getId());
        firstRoutine = routineRepository.save(firstRoutine);

        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        firstTodo.setRoutine(firstRoutine);
        secondTodo.setRoutine(firstRoutine);
        todoRepository.saveAll(List.of(firstTodo, secondTodo));

        BlockRequestDto requestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToRoutinesName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());
    }

    @Test
    public void updateBlock_shouldUpdateBlockWithRoutinesAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());

        Routine routineFirst = routineRepository.save(RoutineFactory.routineInstance());
        routineFirst.setTemplateId(routineFirst.getId());
        routineFirst.setBlock(block);
        routineRepository.save(routineFirst);

        BlockRequestDto requestDto = BlockFactory.updateBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value("Friday Todos"))
            .andExpect(jsonPath(buildJsonPathToId()).exists());
    }
}
