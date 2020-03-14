package com.knighttodo.knighttodo;

public class TestConstants {

    public static final String API_BASE_TODOS = "/todos";
    public static final String API_GET_BY_BLOCK_ID = "/byBlockId";

    public static final String API_BASE_BLOCKS = "/blocks";

    public static final String JSON_ROOT = "$.";

    public static String buildGetBlockByIdUrl(long id) {
        return API_BASE_BLOCKS + "/" + id;
    }

    public static String buildDeleteBlockByIdUrl(long id) {
        return API_BASE_BLOCKS + "/" + id;
    }

    public static String buildGetTodoByIdUrl(long id) {
        return API_BASE_TODOS + "/" + id;
    }

    public static String buildDeleteTodoByIdUrl(long id) {
        return API_BASE_TODOS + "/" + id;
    }

    public static String buildGetTodosByBlockIdUrl(long id) {
        return API_BASE_TODOS + API_GET_BY_BLOCK_ID + "/" + id;
    }

    public static String buildJsonPathToId() {
        return JSON_ROOT + "id";
    }

    public static String buildJsonPathToLength() {
        return JSON_ROOT + "length()";
    }

    public static String buildJsonPathToTodoName() {
        return JSON_ROOT + "todoName";
    }

    public static String buildJsonPathToScaryness() {
        return JSON_ROOT + "scaryness";
    }

    public static String buildJsonPathToHardness() {
        return JSON_ROOT + "hardness";
    }

    public static String buildJsonPathToTodoBlockId() {
        return JSON_ROOT + "todoBlock.id";
    }

    public static String buildJsonPathToBlockName() {
        return JSON_ROOT + "blockName";
    }

    public static String buildJsonPathToTodosLength() {
        return JSON_ROOT + "todos.length()";
    }
}
