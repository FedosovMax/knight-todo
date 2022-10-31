package com.knighttodo.todocore.service.privatedb.mapper;

import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.service.privatedb.representation.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserVO toUserVO(User user);

    User toUser(UserVO userVO);
}
