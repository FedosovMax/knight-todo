package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.enums.Hardness;
import com.knighttodo.knighttodo.entity.enums.Scaryness;

public class TodoFactory {
    private TodoFactory() {
    }

    public static final Long TODO_ID = 1L;
    public static final String TODO_NAME = "hard working";
    public static final Scaryness SCARYNESS_TODO = Scaryness.fromText("NOT_SCARY");
    public static final Hardness HARDNESS_TODO = Hardness.fromText("EXTRAORDINARY");
    public static final Boolean IS_TODO_READY = false;

    public static final Long UPDATE_TODO_ID = 1L;
    public static final String UPDATE_TODO_NAME = "hard working 2";
    public static final Scaryness UPDATE_SCARYNESS_TODO = Scaryness.fromText("SCARY");
    public static final Hardness UPDATE_HARDNESS_TODO = Hardness.fromText("HARD");

    public static Todo firstTodo() {
        return Todo
                .builder()
                .id(TODO_ID)
                .todoName(TODO_NAME)
                .scaryness(SCARYNESS_TODO)
                .hardness(HARDNESS_TODO)
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
                .ready(IS_TODO_READY)
                .build();
    }

}
