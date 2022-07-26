package com.knighttodo.character.factories;

import com.knighttodo.character.rest.request.ExperienceRequestDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceFactory {

    public static final String TODO_ID = "todo_id";
    public static final String USER_ID = "user_id";

    public static ExperienceRequestDto experienceRequestDtoInstance() {
        return ExperienceRequestDto.builder()
            .todoId(TODO_ID).userId(USER_ID).hardness("HARD").scariness("SCARY")
            .build();
    }

    public static ExperienceRequestDto experienceRequestDtoWithoutTodoIdInstance() {
        return ExperienceRequestDto.builder()
            .todoId(null).userId(USER_ID).hardness("HARD").scariness("SCARY")
            .build();
    }
}
