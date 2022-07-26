package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.RoleVO;

import java.util.Optional;

public interface RoleGateway {

    RoleVO save(RoleVO roleVO);

    Optional<RoleVO> findByName(String name);
}
