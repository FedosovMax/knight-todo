package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToHardness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToReadyName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineHardnessInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineIdInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineNameInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineReadyInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToRoutineScarinessInRoutinesListByIndex;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToScariness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTemplateIdName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToBlockId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoIdInTodosListByIndex;
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
    public void createRoutine_shouldSaveRoutineAsTemplateSoItShouldBeAddedToNewBlock() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        todoRepository.saveAll(List.of(firstTodo, secondTodo));

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

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(blockRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
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

        Block savedBlock = blockRepository.findAll().get(1);

        assertThat(savedBlock.getRoutines().size()).isEqualTo(1);
        assertThat(savedBlock.getRoutines().get(0).getTodos().size()).isEqualTo(2);
        assertThat(blockRepository.count()).isEqualTo(2);
        assertThat(todoRepository.count()).isEqualTo(4);
        assertThat(routineRepository.count()).isEqualTo(2);
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
    @Transactional
    public void updateRoutine_shouldUpdateRoutineTodosAndReturnIt_whenRequestIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        Todo firstTodo = TodoFactory.todoWithBlockInstance(block);
        Todo secondTodo = TodoFactory.todoWithBlockInstance(block);
        Todo thirdTodo = TodoFactory.todoWithBlockInstance(block);
        Todo fourthTodo = TodoFactory.todoWithBlockInstance(block);

        firstTodo.setRoutine(routine);
        secondTodo.setRoutine(routine);
        thirdTodo.setRoutine(routine);
        todoRepository.saveAll(List.of(firstTodo, secondTodo, thirdTodo, fourthTodo));

        List<String> updatedRoutineTodoIds = List.of(firstTodo.getId(), secondTodo.getId(), fourthTodo.getId());
        RoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDtoWithTodoIds(updatedRoutineTodoIds);

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(0)).value(firstTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(1)).value(secondTodo.getId()))
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(2)).value(fourthTodo.getId()));

        Routine updatedRoutine = routineRepository.findById(routine.getId()).get();

        assertThat(updatedRoutine.getTodos()).containsAll(List.of(firstTodo, secondTodo, fourthTodo));
        assertThat(updatedRoutine.getTodos()).doesNotContain(thirdTodo);
    }

    @Test
    @Transactional
    public void updateRoutine_shouldUpdateTodosAndCopiedRoutineShouldContainUpdatedTodos() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        Todo firstTodo = todoRepository.save(TodoFactory.todoWithBlockInstance(block));

        List<String> updatedRoutineTodoIds = List.of(firstTodo.getId());
        RoutineRequestDto routineRequestDto = RoutineFactory.updateRoutineRequestDtoWithTodoIds(updatedRoutineTodoIds);
        BlockRequestDto blockRequestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(routineRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToTodoIdInTodosListByIndex(0)).value(firstTodo.getId()));

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(blockRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
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

        Block savedBlock = blockRepository.findAll().get(1);

        assertThat(savedBlock.getRoutines().size()).isEqualTo(1);
        assertThat(savedBlock.getRoutines().get(0).getTodos().size()).isEqualTo(1);
        assertThat(blockRepository.count()).isEqualTo(2);
        assertThat(todoRepository.count()).isEqualTo(2);
        assertThat(routineRepository.count()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updateRoutine_shouldUpdateRoutineNameAndCopiedRoutineShouldContainUpdatedName() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());

        RoutineRequestDto routineRequestDto = RoutineFactory.updateRoutineRequestDto();
        BlockRequestDto blockRequestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + block.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(routineRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToName()).value(routineRequestDto.getName()));

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(blockRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(blockRequestDto.getBlockName()))
            .andExpect(jsonPath(buildJsonPathToRoutineIdInRoutinesListByIndex(0)).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToRoutineNameInRoutinesListByIndex(0)).value(routineRequestDto.getName()))
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        Block savedBlock = blockRepository.findAll().get(1);

        assertThat(savedBlock.getRoutines().size()).isEqualTo(1);
        assertThat(blockRepository.count()).isEqualTo(2);
        assertThat(routineRepository.count()).isEqualTo(2);
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
    @Transactional
    public void deleteRoutine_shouldDeleteRoutine_whenIdIsCorrect() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(block.getId(), routine.getId())))
            .andExpect(status().isOk());

        assertThat(routineRepository.findById(routine.getId())).isEmpty();
    }

    @Test
    @Transactional
    public void deleteRoutine_shouldDeleteRoutineAndNewBlockShouldNotContainIt() throws Exception {
        Block block = blockRepository.save(BlockFactory.BlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.routineInstance());
        Todo todo = TodoFactory.todoWithBlockInstance(block);
        todo.setRoutine(routine);
        todoRepository.save(todo);
        BlockRequestDto blockRequestDto = BlockFactory.createBlockRequestDto();

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(block.getId(), routine.getId())))
            .andExpect(status().isOk());

        mockMvc.perform(post(API_BASE_BLOCKS)
            .content(objectMapper.writeValueAsString(blockRequestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToBlockName()).value(blockRequestDto.getBlockName()))
            .andExpect(jsonPath(buildJsonPathToRoutineIdInRoutinesListByIndex(0)).isEmpty())
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        Block savedBlock = blockRepository.findAll().get(1);

        assertThat(savedBlock.getRoutines()).isEmpty();
        assertThat(blockRepository.count()).isEqualTo(2);
        assertThat(routineRepository.count()).isEqualTo(0);
        assertThat(todoRepository.count()).isEqualTo(0);
    }
}
