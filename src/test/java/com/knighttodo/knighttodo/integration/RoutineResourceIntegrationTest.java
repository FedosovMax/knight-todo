package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToHardness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToReadyName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToScariness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTemplateIdName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoIdInTodosListByIndex;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.BlockFactory;
import com.knighttodo.knighttodo.factories.RoutineFactory;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.BlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
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
public class RoutineResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
        routineRepository.deleteAll();
        blockRepository.deleteAll();
    }

    @Test
    public void createRoutine_shouldAddRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        RoutineRequestDto requestDto = RoutineFactory.createRoutineRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToHardness()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToScariness()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(false))
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void createRoutine_shouldSaveRoutineAsTemplateWithTwoTodos_whenNewRoutineWithTwoNewTodosSaved()
        throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        todoRepository.saveAll(List.of(firstTodo, secondTodo));

        Routine routine = new Routine();
        routine.setBlock(block);
        routine.setTodos(List.of(firstTodo, secondTodo));
        routineRepository.save(routine);

        List<String> todoIds = List.of(firstTodo.getId(), secondTodo.getId());
        RoutineRequestDto routineRequestDto = RoutineFactory.createRoutineWithTodoIdsRequestDto(todoIds);
        BlockRequestDto blockRequestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES)
            .content(objectMapper.writeValueAsString(routineRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToName()).value(routineRequestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToHardness()).value(routineRequestDto.getHardness().toString()))
            .andExpect(jsonPath(buildJsonPathToScariness()).value(routineRequestDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(false))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(0)).value(firstTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(1)).value(secondTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToBlockId()).value(block.getId()))
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());
    }

    @Test
    public void createRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        RoutineRequestDto requestDto = RoutineFactory.createRoutineWithNullNameValueRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(routineRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllRoutines_shouldReturnAllTodos() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        routineRepository.save(RoutineFactory.routineInstance());
        routineRepository.save(RoutineFactory.routineInstance());

        mockMvc.perform(get(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getRoutineById_shouldReturnExistingRoutine_whenIdIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());

        mockMvc.perform(get(buildGetRoutineByIdUrl(block.getId(), routine.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(routine.getId()));
    }

    @Test
    public void updateRoutine_shouldUpdateRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToName()).value(requestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToHardness()).value(requestDto.getHardness().toString()))
            .andExpect(jsonPath(buildJsonPathToScariness()).value(requestDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToBlockId()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(true))
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
        assertThat(routineRepository.findById(routine.getId()).get().getName()).isEqualTo(requestDto.getName());
    }

    @Test
    public void updateRoutine_shouldUpdateRoutineTodosAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        routine.setBlock(block);
        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        Todo thirdTodo = TodoFactory.todoWithBlockInstance(block);
        Todo fourthTodo = TodoFactory.todoWithBlockInstance(block);

        firstTodo.setRoutine(routine);
        secondTodo.setRoutine(routine);
        thirdTodo.setRoutine(routine);
        todoRepository.saveAll(List.of(firstTodo, secondTodo, thirdTodo, fourthTodo));

        List<String> updatedRoutineTodoIds = List.of(firstTodo.getId(), secondTodo.getId(), thirdTodo.getId());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDtoWithTodoIds(updatedRoutineTodoIds);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(0)).value(firstTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(1)).value(secondTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(2)).value(thirdTodo.getId()));

        assertThat(routineRepository.count()).isEqualTo(1);
        assertThat(todoRepository.count()).isEqualTo(4);
    }

    @Test
    public void updateRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineWithNullNaveValueRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRoutine_shouldDeleteRoutine_whenIdIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = RoutineFactory.routineInstance();
        routine.setBlock(block);
        Routine savedRoutine = routineRepository.save(routine);

        Todo todo = TodoFactory.todoWithBlockInstance(block);
        todo.setRoutine(routine);
        todoRepository.save(todo);

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(block.getId(), savedRoutine.getId())))
            .andExpect(status().isOk());

        assertThat(routineRepository.findById(routine.getId())).isEmpty();
        assertThat(routineRepository.count()).isEqualTo(0);
    }

    @Test
    public void deleteRoutine_shouldDeleteRoutineAndNewBlockShouldNotContainIt() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());

        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        routine.setBlock(block);
        routine.setTemplateId(routine.getId());
        Routine savedRoutine = routineRepository.save(routine);

        Todo todo = TodoFactory.todoWithBlockInstance(block);
        todo.setRoutine(routine);
        todoRepository.save(todo);

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(block.getId(), savedRoutine.getId())))
            .andExpect(status().isOk());

        assertThat(blockRepository.count()).isEqualTo(1);
        assertThat(routineRepository.count()).isEqualTo(0);
        assertThat(todoRepository.count()).isEqualTo(0);
    }
}
