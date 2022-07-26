package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.UserVO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserVO save(UserVO userVO);

    List<UserVO> findAll();

    UserVO findById(UUID userId);

    UserVO findByUsername(String username);

    UserVO updateUser(UUID userId, UserVO userVO);

    void deleteById(UUID userId);
}
