package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.gateway.privatedb.mapper.RoleMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.RoleRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RoleGatewayImpl implements RoleGateway {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleVO save(RoleVO roleVO) {
        Role role = roleRepository.save(roleMapper.toRole(roleVO));
        return roleMapper.toRoleVO(role);
    }

    @Override
    public Optional<RoleVO> findByName(String name) {
        return roleRepository.findByName(name).map(roleMapper::toRoleVO);
    }
}
