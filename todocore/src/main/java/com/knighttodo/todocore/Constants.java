package com.knighttodo.todocore;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String API_BASE_URL = "/api";
    public static final String API_BASE_URL_V1 = "/api/v1";
    public static final String API_BASE_TODOS = "/todos";
    public static final String API_BASE_DAYS = "/days";
    public static final String API_BASE_REMINDER = "/reminders";
    public static final String API_BASE_ROUTINES = "/routines";
    public static final String API_BASE_ROUTINES_INSTANCES = "/routineInstances";
    public static final String API_BASE_ROUTINES_TODO_INSTANCES = "/routineTodoInstances";
    public static final String BASE_READY = "/ready";
    public static final String PARAM_READY = "ready";
    public static final String USERS_BASE_URL = "/users";
    public static final String ROLES_BASE_URL = "/roles";
    public static final String AUTHENTICATION_BASE_URL = "/auth";

    /* Character */
    public static final String BASE_CHARACTER = "/character";

    public static final String API_BASE_BONUSES = "/bonuses";

    public static final String API_BASE_SKILLS = "/skills";

    public static final String API_BASE_ITEMS = "/items";

    public static final String API_BASE_WEAPONS = "/weapons";

    public static final String API_BASE_ARMORS = "/armors";

    public static final String BASE_EXPERIENCE_URL = "/experience";

    public static String buildGetUserByIdBaseUrl(UUID userId) {
        return API_BASE_URL_V1 + USERS_BASE_URL + "/" + userId;
    }

    public static String buildUpdateUserBaseUrl(UUID userId) {
        return API_BASE_URL_V1 + USERS_BASE_URL + "/" + userId;
    }

    public static String buildDeleteUserByIdUrl(UUID userId) {
        return API_BASE_URL_V1 + USERS_BASE_URL + "/" + userId;
    }
}
