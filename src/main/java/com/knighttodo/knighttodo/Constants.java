package com.knighttodo.knighttodo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String API_BASE_URL = "/api";
    public static final String API_BASE_TODOS = "/todos";
    public static final String API_GET_TODOS_BY_BLOCK_ID = "/byBlockId";

    public static final String API_BASE_BLOCKS = "/blocks";

    public static final String BASE_EXPERIENCE_URL = "/experience";
    public static final String BASE_READY = "/ready";

    public static final String PARAM_IS_READY = "isReady";

    public static String buildCalculateExperienceBaseUrl() {
        return API_BASE_URL + BASE_EXPERIENCE_URL;
    }
}
