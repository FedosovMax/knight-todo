package com.knighttodo.knighttodo.integration;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.TestConstants.buildDeleteRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildGetRoutineByIdUrl;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToHardness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToId;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToLength;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToReadyName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToScariness;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTemplateIdName;
import static com.knighttodo.knighttodo.TestConstants.buildJsonPathToTodoBlockId;
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
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.routine.request.CreateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.request.UpdateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.RoutineResponseDto;
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
public class RoutineResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @BeforeEach
    public void setUp() {
        routineRepository.deleteAll();
    }

    @Test
    public void createRoutine_shouldAddRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateRoutineRequestDto requestDto = RoutineFactory.createRoutineRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_ROUTINES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(buildJsonPathToName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToHardness()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToScariness()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(false))
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
    }

    @Test
    public void createRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        CreateRoutineRequestDto requestDto = RoutineFactory.createRoutineWithNullValueRequestDto();

        mockMvc.perform(post(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_ROUTINES)
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());

        assertThat(routineRepository.count()).isEqualTo(0);
    }

    @Test
    public void getAllRoutines_shouldReturnAllTodos() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        routineRepository.save(RoutineFactory.createRoutineInstance());
        routineRepository.save(RoutineFactory.createRoutineInstance());

        mockMvc.perform(get(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_ROUTINES))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToLength()).value(2));
    }

    @Test
    public void getRoutineById_shouldReturnExistingRoutine_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.createRoutineInstance());

        mockMvc.perform(get(buildGetRoutineByIdUrl(todoBlock.getId(), routine.getId())))
            .andExpect(status().isFound())
            .andExpect(jsonPath(buildJsonPathToId()).value(routine.getId()));
    }

    @Test
    public void updateRoutine_shouldUpdateRoutineAndReturnIt_whenRequestIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.createRoutineInstance());
        UpdateRoutineRequestDto requestDto = RoutineFactory.updateRoutineRequestDto();
        RoutineResponseDto responseDto = RoutineFactory.createRoutineResponseDto();
        responseDto.setTodoBlockId(todoBlock.getId());

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(buildJsonPathToName()).value(responseDto.getName()))
            .andExpect(jsonPath(buildJsonPathToHardness()).value(responseDto.getHardness().toString()))
            .andExpect(jsonPath(buildJsonPathToScariness()).value(responseDto.getScariness().toString()))
            .andExpect(jsonPath(buildJsonPathToTemplateIdName()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToTodoBlockId()).isNotEmpty())
            .andExpect(jsonPath(buildJsonPathToReadyName()).value(true))
            .andExpect(jsonPath(buildJsonPathToId()).exists());

        assertThat(routineRepository.count()).isEqualTo(1);
        assertThat(routineRepository.findById(routine.getId()).get().getName()).isEqualTo(requestDto.getName());
    }

    @Test
    public void updateRoutine_shouldRespondWithBadRequestStatus_whenNameIsNull() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.createRoutineInstance());
        UpdateRoutineRequestDto requestDto = RoutineFactory.updateRoutineWithNullRequestDto();

        mockMvc.perform(put(API_BASE_BLOCKS + "/" + todoBlock.getId() + API_BASE_ROUTINES + "/" + routine.getId())
            .content(objectMapper.writeValueAsString(requestDto))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteRoutine_shouldDeleteRoutine_whenIdIsCorrect() throws Exception {
        TodoBlock todoBlock = todoBlockRepository.save(TodoBlockFactory.todoBlockInstance());
        Routine routine = routineRepository.save(RoutineFactory.createRoutineInstance());

        mockMvc.perform(delete(buildDeleteRoutineByIdUrl(todoBlock.getId(), routine.getId())))
            .andExpect(status().isOk());

        assertThat(routineRepository.findById(routine.getId())).isEmpty();
    }
}
