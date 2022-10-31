package com.knighttodo.todocore.service.impl;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.exception.RoleNotFoundException;
import com.knighttodo.todocore.service.RoleService;
import com.knighttodo.todocore.service.privatedb.mapper.RoleMapper;
import com.knighttodo.todocore.service.privatedb.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleVO save(RoleVO roleVO) {
        return roleMapper.toRoleVO(roleRepository.save(roleMapper.toRole(roleVO)));
    }

    @Override
    public RoleVO findByName(String name) {
        return roleRepository.findByName(name).map(roleMapper::toRoleVO)
                .orElseThrow(() -> new RoleNotFoundException(String.format("Can't find role with name: %s", name)));
    }
}
