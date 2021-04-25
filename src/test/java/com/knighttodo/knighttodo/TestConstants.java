package com.knighttodo.knighttodo;

import static com.knighttodo.knighttodo.Constants.API_BASE_DAYS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.BASE_READY;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Day;
import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;

public class TestConstants {

    public static final String JSON_ROOT = "$.";
    public static final String PARAMETER_FALSE = "false";
    public static final String PARAMETER_TRUE = "true";

    public static String buildGetDayByIdUrl(String id) {
        return API_BASE_DAYS + "/" + id;
    }

    public static String buildDeleteDayByIdUrl(String id) {
        return API_BASE_DAYS + "/" + id;
    }

    public static String buildGetDayTodoByIdUrl(String dayId, String id) {
        return API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetRoutineTodoByIdUrl(String routineId, String id) {
        return API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetRoutineByIdUrl(String id) {
        return API_BASE_ROUTINES + "/" + id;
    }

    public static String buildDeleteRoutineByIdUrl(String routineId) {
        return API_BASE_ROUTINES + "/" + routineId;
    }

    public static String buildDeleteTodoByIdUrl(String dayId, String id) {
        return API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + id;
    }

    public static String buildDeleteRoutineTodoByIdUrl(String routineId, String id) {
        return API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + id;
    }

    public static String buildGetTodosByDayIdUrl(String dayId) {
        return API_BASE_DAYS + "/" + dayId + API_BASE_TODOS;
    }

    public static String buildGetRoutineTodosByDayIdUrl(String routineId) {
        return API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS;
    }

    public static String buildUpdateTodoReadyBaseUrl(String dayId, String dayTodoId) {
        return API_BASE_DAYS + "/" + dayId + API_BASE_TODOS + "/" + dayTodoId + BASE_READY;
    }

    public static String buildUpdateRoutineTodoReadyBaseUrl(String routineId, String routineTodoId) {
        return API_BASE_ROUTINES + "/" + routineId + API_BASE_TODOS + "/" + routineTodoId + BASE_READY;
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

    public static String buildJsonPathToExperience() {
        return JSON_ROOT + "experience";
    }

    public static String buildJsonPathToDayName() {
        return JSON_ROOT + "dayName";
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

    public static String buildJsonPathToRoutineTodoIdInTodosListByIndex(int index) {
        return JSON_ROOT + "routineTodos.[" + index + "].id";
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
