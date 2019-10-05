package com.knighttodo.knighttodo.factories;


import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;

import java.util.Arrays;
import java.util.List;

public final class TodoBlockFactory {
    private TodoBlockFactory(){}

    public static final Long TODO_BLOCK_ID = 1L;
    public static final String BLOCK_NAME = "for sunday";
    public static final List<Todo> TODO_LIST = Arrays.asList(TodoFactory.firstTodo());

    public static TodoBlock firstTodoBlock() {
        return TodoBlock
                .builder()
                .id(TODO_BLOCK_ID)
                .blockName(BLOCK_NAME)
                .todoList(TODO_LIST)
                .build();
    }

}
