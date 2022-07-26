package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.rest.request.RoleRequestDto;
import com.knighttodo.todocore.rest.response.RoleResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestRoleMapper {

    RoleVO toRoleVO(RoleRequestDto roleRequestDto);

    RoleResponseDto toRoleResponseDto(RoleVO roleVO);
}
