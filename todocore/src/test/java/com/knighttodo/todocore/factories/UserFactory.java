package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.service.privatedb.representation.User;
import com.knighttodo.todocore.rest.request.UserRequestDto;

public class UserFactory {

    public static final String USER_LOGIN = "login";
    public static final String CHANGED_USER_LOGIN = "changedLogin";
    public static final String USER_PASSWORD = "password";
    public static final String CHANGED_USER_PASSWORD = "changedPassword";

    private UserFactory() {
    }

    public static UserRequestDto createUserRequestDtoInstance() {
        return UserRequestDto.builder()
            .login(USER_LOGIN)
            .password(USER_PASSWORD)
            .build();
    }

    public static UserRequestDto createUserRequestDtoWithoutLoginInstance() {
        return UserRequestDto.builder()
            .login(null)
            .password(USER_PASSWORD)
            .build();
    }

    public static UserRequestDto createUserRequestDtoWithEmptyLoginInstance() {
        return UserRequestDto.builder()
            .login(" ")
            .password(USER_PASSWORD)
            .build();
    }

    public static UserRequestDto updateUserRequestDtoInstance() {
        return UserRequestDto.builder()
            .login(CHANGED_USER_LOGIN)
            .password(CHANGED_USER_PASSWORD)
            .build();
    }

    public static UserRequestDto updateUserRequestDtoWithoutLoginInstance() {
        return UserRequestDto.builder()
            .login(null)
            .password(CHANGED_USER_PASSWORD)
            .build();
    }

    public static UserRequestDto updateUserRequestDtoWithLoginConsistingSpacesInstance() {
        return UserRequestDto.builder()
            .login(" ")
            .password(CHANGED_USER_PASSWORD)
            .build();
    }

    public static UserRequestDto updateUserRequestDtoWithoutPasswordInstance() {
        return UserRequestDto.builder()
            .login(CHANGED_USER_LOGIN)
            .password(null)
            .build();
    }

    public static User createUserInstance() {
        return User.builder()
            .login(USER_LOGIN)
            .password(USER_PASSWORD)
            .build();
    }
}
