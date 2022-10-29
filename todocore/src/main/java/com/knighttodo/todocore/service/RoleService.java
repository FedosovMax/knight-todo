package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.RoleVO;

public interface RoleService {

    RoleVO save(RoleVO roleVO);

    RoleVO findByName(String name);
}
