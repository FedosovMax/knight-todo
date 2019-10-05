package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;

public class TodoFactory {
    private TodoFactory(){}

    public static final Long TODO_ID = 1L;
    public static final String TODO_NAME = "for sunday";
    public static final TodoBlock TODO_BLOCK = new TodoBlock(TODO_ID,TODO_NAME,TodoBlockFactory.TODO_LIST);

    public static Todo firstTodo(){
        return new Todo(TODO_ID,TODO_NAME,TODO_BLOCK);
    }

}
