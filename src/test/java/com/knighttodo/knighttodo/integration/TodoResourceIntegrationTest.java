package com.knighttodo.knighttodo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import com.knighttodo.knighttodo.utilis.TestUtils;
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
public class TodoResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void deleteTodoTest() throws Exception {

        Todo todo = TodoFactory.firstTodo();
        todoRepository.save(todo);

        mockMvc.perform(
                delete("/todos/todo/" + todo.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk());

        assertThat(todoRepository.count()).isEqualTo(0);
    }

    @Test
    public void updateTodoTest() throws Exception {

        Todo firstTodo = TodoFactory.firstTodo();

        todoRepository.save(firstTodo);

        Todo updateTodo = TodoFactory.updateTodo();

        mockMvc.perform(
                put("/todos/todo")
                        .content(objectMapper.writeValueAsString(updateTodo))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());

        assertThat(todoRepository.findById(updateTodo.getId()).get().getTodoName())
                .isEqualTo(updateTodo.getTodoName());
    }

    @Test
    public void loadTodoByIdTest() throws Exception {
        Todo todo = TodoFactory.firstTodo();

        todoRepository.save(todo);

        mockMvc.perform(get("/todos/todo/" + 1L)
                .content(TestUtils.convertObjectToJsonBytes(todo))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isFound());
//            .andExpect(jsonPath("$.todoName").value("hard working"));
    }

    @Test
    public void saveTodoTest() throws Exception {

        Todo todo = TodoFactory.firstTodo();

        mockMvc.perform(
                post("/todos/todo")
                        .content(objectMapper.writeValueAsString(todo))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        assertThat(todoRepository.getOne(TodoFactory.firstTodo().getId())).isNotNull();
    }

}
