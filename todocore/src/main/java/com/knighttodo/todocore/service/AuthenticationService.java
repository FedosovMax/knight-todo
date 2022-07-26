package com.knighttodo.todocore.service;

import com.knighttodo.todocore.rest.request.AuthenticationRequestDto;
import com.knighttodo.todocore.rest.response.AuthenticationResponseDto;

public interface AuthenticationService {

    AuthenticationResponseDto login(AuthenticationRequestDto requestDto);
}
