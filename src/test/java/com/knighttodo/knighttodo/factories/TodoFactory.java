package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;

public class TodoFactory {
    private TodoFactory(){}

    public static final Long TODO_ID = 1L;
    public static final String TODO_NAME = "hard working";
    public static final TodoBlock TODO_BLOCK = new TodoBlock(TODO_ID,TODO_NAME, TodoBlockFactory.TODO_LIST);

    public static final Long UPDATE_TODO_ID = 2L;
    public static final String UPDATE_TODO_NAME = "hard working 2";
    public static final TodoBlock UPDATE_TODO_BLOCK = new TodoBlock(UPDATE_TODO_ID,UPDATE_TODO_NAME,TodoBlockFactory.UPDATE_TODO_LIST);

    public static Todo firstTodo(){
        return new Todo(TODO_ID,TODO_NAME,TODO_BLOCK);
    }

    public static Todo updateTodo(){
        return new Todo(UPDATE_TODO_ID,UPDATE_TODO_NAME,UPDATE_TODO_BLOCK);
    }

}
