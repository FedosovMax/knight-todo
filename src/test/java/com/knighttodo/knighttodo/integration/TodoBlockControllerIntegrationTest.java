package com.knighttodo.knighttodo.integration;

import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
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
public class TodoBlockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoBlockService todoBlockService;

    @Autowired
    private TodoBlockRepository todoBlockRepository;

    @Test
    public void deleteTodoBlockTest() throws Exception {

        TodoBlock todoBlock = TodoBlockFactory.firstTodoBlock();
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

        TodoBlock firstTodoBlock = TodoBlockFactory.firstTodoBlock();

        todoBlockRepository.save(firstTodoBlock);

        TodoBlock updateBlock = TodoBlockFactory.updateTodoBlock();

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
        TodoBlock todoBlock = TodoBlockFactory.firstTodoBlock();

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

        TodoBlock todoBlock = TodoBlockFactory.firstTodoBlock();

        mockMvc.perform(
         post("/blocks/block")
                .content(TestUtils.convertObjectToJsonBytes(todoBlock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated());

        assertThat(todoBlockRepository.getOne(TodoBlockFactory.firstTodoBlock().getId())).isNotNull();
    }

}
