package com.knighttodo.knighttodo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.knighttodo.knighttodo.factories.TodoFactory;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.utilis.TestUtils;
import javax.transaction.Transactional;
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
public class TodoBlockResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoBlockService todoBlockService;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @Test
    public void deleteTodoBlockTest() throws Exception {

        TodoBlock todoBlock = TodoFactory.firstTodoBlock();
        todoBlockRepository.save(todoBlock);

        mockMvc.perform(
            delete("/blocks/block/" + todoBlock.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
            .andExpect(status().isOk());

        assertThat(todoBlockRepository.count()).isEqualTo(1);
    }

    @Transactional
    @Test
    public void updateTodoBlockTest() throws Exception {

        TodoBlock firstTodoBlock = TodoFactory.firstTodoBlock();

        todoBlockRepository.save(firstTodoBlock);

        TodoBlock updateBlock = TodoFactory.updateTodoBlock();

        mockMvc.perform(
            put("/blocks/block")
                .content(TestUtils.convertObjectToJsonBytes(updateBlock))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk());

        assertThat(todoBlockRepository.findById(updateBlock.getId()).get().getBlockName())
            .isEqualTo(updateBlock.getBlockName());
    }

    @Test
    public void loadTodoBlockByIdTest() throws Exception {
        TodoBlock todoBlock = TodoFactory.firstTodoBlock();

        todoBlockRepository.save(todoBlock);

        mockMvc.perform(get("/blocks/block/" + todoBlock.getId())
            .content(TestUtils.convertObjectToJsonBytes(todoBlock))
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.blockName").value("for sunday"));
    }

    @Test
    public void saveTodoBlockTest() throws Exception {

        TodoBlock todoBlock = TodoFactory.firstTodoBlock();

        mockMvc.perform(
            post("/blocks/block")
                .content(TestUtils.convertObjectToJsonBytes(todoBlock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());

        assertThat(todoBlockRepository.getOne(TodoFactory.firstTodoBlock().getId())).isNotNull();
    }

}
