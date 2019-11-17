package com.knighttodo.knighttodo.factories;


import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TodoBlockFactory {
    private TodoBlockFactory(){}

    public static final Long TODO_BLOCK_ID = 1L;
    public static final String BLOCK_NAME = "for sunday";
    public static final List<Todo> TODO_LIST = Arrays.asList(TodoFactory.firstTodo());

    public static final Long UPDATE_TODO_BLOCK_ID = 2L;
    public static final String UPDATE_BLOCK_NAME = "for friday";
    public static final List<Todo> UPDATE_TODO_LIST = Arrays.asList(TodoFactory.updateTodo());



    public static TodoBlock firstTodoBlock() {
        return TodoBlock
                .builder()
                .id(TODO_BLOCK_ID)
                .blockName(BLOCK_NAME)
                .todoList(new ArrayList<>())
                .build();
    }

    public static TodoBlock updateTodoBlock() {
        return TodoBlock
                .builder()
                .id(UPDATE_TODO_BLOCK_ID)
                .blockName(UPDATE_BLOCK_NAME)
                .todoList(new ArrayList<>())
                .build();
    }
}
