package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.rest.mapper.RestUserMapper;
import com.knighttodo.todocore.rest.request.UserRequestDto;
import com.knighttodo.todocore.rest.response.UserResponseDto;
import com.knighttodo.todocore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;
import static com.knighttodo.todocore.Constants.USERS_BASE_URL;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + USERS_BASE_URL)
@Slf4j
public class UserResource {

    private final UserService userService;
    private final RestUserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Rest request to add user : {}", userRequestDto);
        UserVO userVO = userService.save(userMapper.toUserVO(userRequestDto));
        return new ResponseEntity<>(userMapper.toUserResponseDto(userVO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Rest request to get all users");
        return new ResponseEntity<>(userService.findAll()
            .stream().map(userMapper::toUserResponseDto).collect(toList()), HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID userId) {
        log.info("Rest request to get user by id : {}", userId);
        return new ResponseEntity<>(userMapper.toUserResponseDto(userService.findById(userId)), HttpStatus.FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID userId,
        @Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Rest request to update user : {}", userRequestDto);
        UserVO userVo = userService.updateUser(userId, userMapper.toUserVO(userRequestDto));
        return new ResponseEntity<>(userMapper.toUserResponseDto(userVo), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        log.info("Rest request to delete user by id : {}", userId);
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
