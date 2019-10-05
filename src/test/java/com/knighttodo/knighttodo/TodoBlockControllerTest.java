package com.knighttodo.knighttodo;

import com.knighttodo.knighttodo.controller.TodoBlockController;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.factories.TodoBlockFactory;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.service.TodoService;
import com.knighttodo.knighttodo.utilis.TestUtils;
import org.apache.kerby.kerberos.kerb.provider.TokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.util.Arrays.asList;
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
@WithMockUser
@MockBeans(value = {
        @MockBean(UserDetailsService.class),
        @MockBean(TokenProvider.class)
})
public class TodoBlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoBlockService todoBlockService;

    @MockBean
    private TodoBlockRepository todoBlockRepository;

    @Test
    public void saveTodoBlockTest() throws Exception {
        mockMvc.perform(
                post("/todoBlockController/list")
                        .content(TestUtils.convertObjectToJsonBytes(TodoBlockFactory.firstTodoBlock()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isCreated());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void deleteTodoBlockTest() throws Exception {

        TodoBlock todoBlock = TodoBlockFactory.firstTodoBlock();

        mockMvc.perform(
                delete("/todoBlockController/list/{1}", 1)
                        .content(TestUtils.convertObjectToJsonBytes(todoBlock))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk());
    }


    @Test
    public void updateTodoBlockTest() throws Exception {

        TodoBlock todoBlock = TodoBlockFactory.firstTodoBlock();

        when(todoBlockService.updateTodoBlock(eq(todoBlock)))
                .thenReturn(todoBlock);

        mockMvc.perform(
                put("/todoBlockController/list/{1}", 1)
                        .content(TestUtils.convertObjectToJsonBytes(todoBlock))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void loadTodoBlockByIdTest() throws Exception{
        given(todoBlockService.findById(1L)).willReturn(TodoBlockFactory.firstTodoBlock());

        mockMvc.perform(get("/todoBlockController/list/{1}", 1)
                .content(TestUtils.convertObjectToJsonBytes(TodoBlockFactory.firstTodoBlock()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isFound());
//                .andExpect(jsonPath("$.name").value("Java for sceptics"));
    }

    @Test
    public void findAllTodoBlockTest() throws Exception {
        given(todoBlockService.findAll()).willReturn(asList(
                TodoBlockFactory.firstTodoBlock()
        ));

        mockMvc.perform(get("/todoBlockController/list")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isFound());
    }

}
