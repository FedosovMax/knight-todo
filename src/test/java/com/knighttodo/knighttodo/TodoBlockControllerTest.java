package com.knighttodo.knighttodo;

import com.knighttodo.knighttodo.controller.TodoBlockController;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.utilis.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoBlockController.class, secure = false)
public class TodoBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoBlockService todoBlockService;

    @MockBean
    private TodoBlockRepository todoBlockRepository;

    @Test
    public void saveTodoBlockTest() throws Exception { // GOOD
        mockMvc.perform(
                post("/todoBlockController/block")
                        .content(TestUtils.convertObjectToJsonBytes(TodoBlockFactory.firstTodoBlock()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void deleteTodoBlockTest() throws Exception { // GOOD

        todoBlockRepository.save(TodoBlockFactory.firstTodoBlock());

        mockMvc.perform(
                delete("/todoBlockController/block/{1}", 1)
                        .content(TestUtils.convertObjectToJsonBytes(TodoBlockFactory.firstTodoBlock()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk());
        assertThat(todoBlockRepository.count()).isEqualTo(0);
    }

    @Test
    public void updateTodoBlockTest() throws Exception { // NOT GOOD

        todoBlockRepository.save(TodoBlockFactory.firstTodoBlock());

        TodoBlock updateBlock = TodoBlockFactory.updateTodoBlock();

        mockMvc.perform(
                put("/todoBlockController/block/")
                        .content(TestUtils.convertObjectToJsonBytes(updateBlock))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk());
        assertThat(todoBlockRepository.getOne(updateBlock.getId())).isEqualTo(updateBlock);
    }

    @Test
    public void loadTodoBlockByIdTest() throws Exception{ // GOOD
        given(todoBlockService.findById(1L)).willReturn(TodoBlockFactory.firstTodoBlock());

        mockMvc.perform(get("/todoBlockController/block/{1}", 1)
                .content(TestUtils.convertObjectToJsonBytes(TodoBlockFactory.firstTodoBlock()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.blockName").value("for sunday"));
    }

    @Test
    public void findAllTodoBlockTest() throws Exception { // GOOD
        given(todoBlockService.findAll()).willReturn(asList(
                TodoBlockFactory.firstTodoBlock()
        ));

        mockMvc.perform(get("/todoBlockController/block")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].blockName", is("for sunday")));
    }

}
