package com.knighttodo.knighttodo;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.API_GET_TODOS_BY_BLOCK_ID;
import static com.knighttodo.knighttodo.Constants.BASE_READY;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_FALSE = "false";
    public static final String PARAMETER_TRUE = "true";

    public static String buildGetBlockByIdUrl(String id) {
        return API_BASE_BLOCKS + "/" + id;
    }

    public static String buildDeleteBlockByIdUrl(String id) {
        return API_BASE_BLOCKS + "/" + id;
    }

    public static String buildGetTodoByIdUrl(String blockId, String id) {
        return API_BASE_BLOCKS + "/" + blockId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetRoutineByIdUrl(String blockId, String id) {
        return API_BASE_BLOCKS + "/" + blockId + API_BASE_ROUTINES + "/" + id;
    }

    public static String buildDeleteRoutineByIdUrl(String blockId, String id) {
        return API_BASE_BLOCKS + "/" + blockId + API_BASE_ROUTINES + "/" + id;
    }

    public static String buildDeleteTodoByIdUrl(String blockId, String id) {
        return API_BASE_BLOCKS + "/" + blockId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetTodosByBlockIdUrl(String blockId) {
        return API_BASE_BLOCKS + "/" + blockId + API_BASE_TODOS + API_GET_TODOS_BY_BLOCK_ID;
    }

    public static String buildUpdateTodoReadyBaseUrl(Block block, Todo todo) {
        return API_BASE_BLOCKS + "/" + block.getId() + API_BASE_TODOS + "/" + todo.getId() + BASE_READY;
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

    public static String buildJsonPathToName() {
        return JSON_ROOT + "name";
    }

    public static String buildJsonPathToScariness() {
        return JSON_ROOT + "scariness";
    }

    public static String buildJsonPathToHardness() {
        return JSON_ROOT + "hardness";
    }

    public static String buildJsonPathToBlockId() {
        return JSON_ROOT + "blockId";
    }

    public static String buildJsonPathToExperience() {
        return JSON_ROOT + "experience";
    }

    public static String buildJsonPathToBlockName() {
        return JSON_ROOT + "blockName";
    }

    public static String buildJsonPathToTemplateIdName() {
        return JSON_ROOT + "templateId";
    }

    public static String buildJsonPathToReadyName() {
        return JSON_ROOT + "ready";
    }

    public static String buildJsonPathToRoutinesName() {
        return JSON_ROOT + "routines";
    }

    public static String buildJsonPathToTodoIdInTodosListByIndex(int index) {
        return JSON_ROOT + "todos.[" + index + "].id";
    }

    public static String buildJsonPathToRoutineIdInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].id";
    }

    public static String buildJsonPathToRoutineNameInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].name";
    }

    public static String buildJsonPathToRoutineHardnessInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].hardness";
    }

    public static String buildJsonPathToRoutineScarinessInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].scariness";
    }

    public static String buildJsonPathToRoutineReadyInRoutinesListByIndex(int index) {
        return JSON_ROOT + "routines.[" + index + "].ready";
    }

    public static String buildJsonPathToTodoIdInTodosListInRoutinesListByIndexes(int routineIndex, int todoIndex) {
        return JSON_ROOT + "routines.[" + routineIndex + "].todos[" + todoIndex + "].id";
    }
}
