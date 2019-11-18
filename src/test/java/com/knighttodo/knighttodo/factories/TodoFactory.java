package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoFactory {

    public static final Long TODO_ID = 1L;
    public static final String TODO_NAME = "hard working";
    public static final Scaryness SCARYNESS_TODO = Scaryness.fromText("NOT_SCARY");
    public static final Hardness HARDNESS_TODO = Hardness.fromText("EXTRAORDINARY");
    public static final Boolean IS_TODO_READY = false;
    public static final Long UPDATE_TODO_ID = 1L;
    public static final String UPDATE_TODO_NAME = "hard working 2";
    public static final Scaryness UPDATE_SCARYNESS_TODO = Scaryness.fromText("SCARY");
    public static final Hardness UPDATE_HARDNESS_TODO = Hardness.fromText("HARD");

    public static final Long TODO_BLOCK_ID = 1L;
    public static final String BLOCK_NAME = "for sunday";
    public static final List<Todo> TODO_LIST = Arrays.asList(firstTodo(), updateTodo());

    public static final Long UPDATE_TODO_BLOCK_ID = 2L;
    public static final String UPDATE_BLOCK_NAME = "for friday";
    public static final List<Todo> UPDATE_TODO_LIST = Arrays.asList(updateTodo());

    private TodoFactory() {
    }

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

    public static Todo firstTodo() {
        return Todo
            .builder()
            .id(TODO_ID)
            .todoName(TODO_NAME)
            .scaryness(SCARYNESS_TODO)
            .hardness(HARDNESS_TODO)
            .todoBlock(firstTodoBlock())
            .ready(IS_TODO_READY)
            .build();
    }

    public static Todo updateTodo() {
        return Todo
            .builder()
            .id(UPDATE_TODO_ID)
            .todoName(UPDATE_TODO_NAME)
            .scaryness(UPDATE_SCARYNESS_TODO)
            .hardness(UPDATE_HARDNESS_TODO)
            .todoBlock(firstTodoBlock())
            .ready(IS_TODO_READY)
            .build();
    }

}
