package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.rest.request.AuthenticationRequestDto;
import com.knighttodo.todocore.rest.response.AuthenticationResponseDto;
import com.knighttodo.todocore.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static com.knighttodo.todocore.Constants.AUTHENTICATION_BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + AUTHENTICATION_BASE_URL)
@Slf4j
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        log.info("Rest request to login : {}", requestDto);
        return ResponseEntity.ok().body(authenticationService.login(requestDto));
    }
}
