package com.knighttodo.knighttodo;

import java.util.UUID;

import static com.knighttodo.knighttodo.Constants.*;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_FALSE = "false";
    public static final String PARAMETER_TRUE = "true";

    public static String buildGetDayByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + id;
    }

    public static String buildDeleteDayByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + id;
    }

    public static String buildGetDayTodoByIdUrl(UUID dayId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetRoutineTodoByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetRoutineByIdUrl(UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + id;
    }

    public static String buildGetRoutineInstanceByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_ROUTINES_INSTANCES + "/" + id;
    }

    public static String buildDeleteRoutineByIdUrl(UUID routineId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId;
    }

    public static String buildDeleteRoutineInstanceByIdUrl(UUID routineId, UUID routineInstanceId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_ROUTINES_INSTANCES + "/" + routineInstanceId;
    }

    public static String buildDeleteTodoByIdUrl(UUID dayId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }

    public static String buildDeleteRoutineTodoByIdUrl(UUID routineId, UUID id) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetTodosByDayIdUrl(UUID dayId) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS;
    }

    public static String buildGetRoutineTodosByDayIdUrl(UUID routineId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS;
    }

    public static String buildUpdateTodoReadyBaseUrl(UUID dayId, UUID dayTodoId) {
        return API_BASE_URL_V1 + API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + dayTodoId + BASE_READY;
    }

    public static String buildUpdateRoutineTodoReadyBaseUrl(UUID routineId, UUID routineTodoId) {
        return API_BASE_URL_V1 + API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + routineTodoId + BASE_READY;
    }

    public static String buildJsonPathToId() {
        return JSON_ROOT + "id";
    }

    public static String buildJsonPathToLength() {
        return JSON_ROOT + "length()";
    }

    public static String buildJsonPathToTodoName() {
        return JSON_ROOT + "dayTodoName";
    }

    public static String buildJsonPathToRoutineTodoName() {
        return JSON_ROOT + "routineTodoName";
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

    public static String buildJsonPathToDayId() {
        return JSON_ROOT + "dayId";
    }

    public static String buildJsonPathToRoutineId() {
        return JSON_ROOT + "routineId";
    }

    public static String buildJsonPathToRoutineInstanceId() {
        return JSON_ROOT + "routineInstance";
    }

    public static String buildJsonPathToExperience() {
        return JSON_ROOT + "experience";
    }

    public static String buildJsonPathToDayName() {
        return JSON_ROOT + "dayName";
    }

    public static String buildJsonPathToReadyName() {
        return JSON_ROOT + "ready";
    }

    public static String buildJsonPathToRoutinesName() {
        return JSON_ROOT + "routines";
    }

    public static String buildJsonPathToRoutineTodoIdInTodosListByIndex(int index) {
        return JSON_ROOT + "routineTodos.[" + index + "].id";
    }

    public static String buildJsonPathToRoutineInstanceIdInInstancesListByIndex(int index) {
        return JSON_ROOT + "routineInstances.[" + index + "].id";
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
