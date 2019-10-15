package com.knighttodo.knighttodo.integration;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.repository.TodoRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
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

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

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

        assertThat(todoRepository.count()).isEqualTo(1);
    }

    @Transactional
    @Test
    public void updateTodoTest() throws Exception {

        Todo firstTodo = TodoFactory.firstTodo();

        todoRepository.save(firstTodo);

        Todo update = TodoFactory.updateTodo();

        mockMvc.perform(
            put("/todos/todo")
                .content(TestUtils.convertObjectToJsonBytes(update))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk());

        assertThat(todoRepository.findById(update.getId()).get().getTodoName())
            .isEqualTo(update.getTodoName());
    }

    @Test
    public void loadTodoByIdTest() throws Exception {
        Todo todo = TodoFactory.firstTodo();

        todoRepository.save(todo);

        mockMvc.perform(get("/todos/block/" + todo.getId())
            .content(TestUtils.convertObjectToJsonBytes(todo))
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.todoName").value("hard working"));
    }

    @Test
    public void saveTodoTest() throws Exception {

        Todo todo = TodoFactory.firstTodo();

        mockMvc.perform(
         post("/todos/block")
                .content(TestUtils.convertObjectToJsonBytes(todo))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());

        assertThat(todoRepository.getOne(TodoFactory.firstTodo().getId())).isNotNull();
    }

}
