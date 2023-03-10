package com.knighttodo.todocore.gateway.privatedb.mapper;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.gateway.privatedb.representation.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleVO toRoleVO(Role role);

    Role toRole(RoleVO roleVO);
}
