package com.knighttodo.knighttodo;

public class TestConstants {

    public static final String API_BASE_TODOS = "/todos/";
    public static final String API_GET_TODOS_BY_PATH_VARIABLE_BLOCK_ID = API_BASE_TODOS + "byBlockId/";

    public static final String JSON_ROOT = "$.";
    public static final String JSON_PATH_ID = JSON_ROOT + "id";
    public static final String JSON_PATH_LENGTH = JSON_ROOT + "length()";
    public static final String JSON_PATH_TODO_NAME = JSON_ROOT + "todoName";
    public static final String JSON_PATH_SCARYNESS = JSON_ROOT + "scaryness";
    public static final String JSON_PATH_HARDNESS = JSON_ROOT + "hardness";
    public static final String JSON_PATH_TODO_BLOCK_ID = JSON_ROOT + "todoBlock.id";

    public static final String API_BASE_BLOCKS = "/blocks";
    
    public static final String JSON_PATH_BLOCK_NAME = JSON_ROOT + "blockName";
    public static final String JSON_PATH_TODOS_LENGTH = JSON_ROOT + "todos.length()";
}
