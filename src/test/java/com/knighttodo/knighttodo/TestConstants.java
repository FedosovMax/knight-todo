package com.knighttodo.knighttodo;

public class TestConstants {

    public static final String API_BASE_TODOS = "/todos";
    public static final String API_GET_TODOS_BY_PATH_VARIABLE_BLOCK_ID = "/todos/byBlockId";

    public static final String JSON_ROOT = "$.";

    public static final String API_BASE_BLOCKS = "/blocks";

    public static String getJsonPathToId(){
        return JSON_ROOT + "id";
    }

    public static String getJsonPathToLength() {
        return JSON_ROOT + "length()";
    }

    public static String getJsonPathToTodoName() {
        return JSON_ROOT + "todoName";
    }

    public static String getJsonPathToScaryness() {
        return JSON_ROOT + "scaryness";
    }

    public static String getJsonPathToHardness() {
        return JSON_ROOT + "hardness";
    }

    public static String getJsonPathToTodoBlockId() {
        return JSON_ROOT + "todoBlock.id";
    }

    public static String getJsonPathToBlockName(){
        return JSON_ROOT + "blockName";
    }

    public static String getJsonPathToTodosLength(){
        return JSON_ROOT + "todos.length()";
    }
}
