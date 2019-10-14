package com.knighttodo.knighttodo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TodoBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoBlockRepository todoBlockRepository;

    @MockBean
    private TodoBlockService todoBlockService;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addTodoBlockTest() throws Exception {
        mockMvc.perform(
                post("/blocks/block")
                        .content(objectMapper.writeValueAsString(TodoBlockFactory.firstTodoBlock()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void getTodoBlockByIdTest() throws Exception {
        given(todoBlockService.findById(1L)).willReturn(TodoBlockFactory.firstTodoBlock());

        mockMvc.perform(get("/blocks/block/" + 1L))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void updateTodoBlockTest() throws Exception {
        when(todoBlockService.updateTodoBlock(eq(TodoBlockFactory.updateTodoBlock())))
                .thenReturn(TodoBlockFactory.updateTodoBlock());
        mockMvc.perform(
                put("/blocks/block")
                        .content(objectMapper.writeValueAsBytes(TodoBlockFactory.updateTodoBlock()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void deleteTodoBlockTest() throws Exception {
        mockMvc.perform(delete("/blocks/block/" + 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

}
