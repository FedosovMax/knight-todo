package com.knighttodo.knighttodo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String API_BASE_URL = "/api";
    public static final String API_BASE_URL_V1 = "/api/v1/";
    public static final String API_BASE_TODOS = "/todos";

    public static final String API_BASE_DAYS = "/days";

    public static final String API_BASE_ROUTINES = "/routines";
    public static final String API_BASE_ROUTINES_INSTANCES = "/routineInstances";

    public static final String BASE_EXPERIENCE_URL = "/experience";
    public static final String BASE_READY = "/ready";

    public static final String PARAM_READY = "ready";
}
