package com.knighttodo.todocore.service.impl;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.exception.UserNotFoundException;
import com.knighttodo.todocore.gateway.UserGateway;
import com.knighttodo.todocore.service.RoleService;
import com.knighttodo.todocore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVO save(UserVO userVO) {
        RoleVO roleUser = roleService.findByName("ROLE_USER");
        userVO.setRoles(List.of(roleUser));
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        return userGateway.save(userVO);
    }

    @Override
    public List<UserVO> findAll() {
        return userGateway.findAll();
    }

    @Override
    public UserVO findById(UUID userId) {
        return userGateway.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(String.format("Can't find user with id: %s", userId)));
    }

    @Override
    public UserVO findByUsername(String username) {
        return userGateway.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("Can't find user with username: %s",
                                                                           username)));
    }

    @Override
    public UserVO updateUser(UUID userId, UserVO userVO) {
        UserVO dbUser = findById(userId);

        dbUser.setLogin(userVO.getLogin());
        dbUser.setPassword(userVO.getPassword());

        return userGateway.save(dbUser);
    }

    @Override
    public void deleteById(UUID userId) {
        userGateway.deleteById(userId);
    }
}
