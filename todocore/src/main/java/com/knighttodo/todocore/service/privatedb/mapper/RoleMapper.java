package com.knighttodo.todocore.service.privatedb.mapper;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.service.privatedb.representation.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleVO toRoleVO(Role role);

    Role toRole(RoleVO roleVO);
}
