package com.knighttodo.knighttodo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.repository.TodoRepository;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
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
@AutoConfigureMockMvc
@SpringBootTest
public class TodoIntegrationTests {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllTest() throws Exception {
        List<Todo> list = List.of(TodoFactory.firstTodo(), TodoFactory.firstTodo(), TodoFactory.updateTodo());

        list.forEach(todoRepository::save);

        mvc.perform(get("/todos/todo").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isFound());

        assertThat(todoRepository.count()).isEqualTo(2);
    }

    @Test
    public void addTodoTest() throws Exception {
        mvc.perform(post("/todos/todo").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(TodoFactory.firstTodo())))
            .andExpect(status().isCreated());

        assertThat(todoRepository.count()).isEqualTo(2);
    }

    @Test
    public void getTodoByIdTest() throws Exception {

        Todo todo = TodoFactory.firstTodo();

        todoRepository.save(todo);

        mvc.perform(get("/todos/todo/" + 1)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isFound());

        assertThat(todoRepository.count()).isEqualTo(2);
    }
}
