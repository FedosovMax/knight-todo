package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.rest.request.UserRequestDto;
import com.knighttodo.todocore.rest.response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RestRoleMapper.class)
public interface RestUserMapper {

    UserVO toUserVO(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(UserVO userVO);
}
