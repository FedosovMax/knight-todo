package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.UserVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {

    UserVO save(UserVO userVO);

    List<UserVO> findAll();

    Optional<UserVO> findById(UUID userId);

    Optional<UserVO> findByLogin(String login);

    void deleteById(UUID userId);
}
