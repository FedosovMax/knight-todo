package com.knighttodo.todocore.service.impl;

import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.rest.request.AuthenticationRequestDto;
import com.knighttodo.todocore.rest.response.AuthenticationResponseDto;
import com.knighttodo.todocore.security.jwt.JwtTokenProvider;
import com.knighttodo.todocore.service.AuthenticationService;
import com.knighttodo.todocore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public AuthenticationResponseDto login(AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        UserVO userVO = userService.findByUsername(username);
        String token = jwtTokenProvider.createToken(userVO);
        return AuthenticationResponseDto.builder().username(username).token(token).build();
    }
}
