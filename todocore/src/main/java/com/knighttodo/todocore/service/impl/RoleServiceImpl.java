package com.knighttodo.todocore.service.impl;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.exception.RoleNotFoundException;
import com.knighttodo.todocore.gateway.RoleGateway;
import com.knighttodo.todocore.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleGateway roleGateway;

    @Override
    public RoleVO save(RoleVO roleVO) {
        return roleGateway.save(roleVO);
    }

    @Override
    public RoleVO findByName(String name) {
        return roleGateway.findByName(name)
            .orElseThrow(() -> new RoleNotFoundException(String.format("Can't find role with name: %s", name)));
    }
}
