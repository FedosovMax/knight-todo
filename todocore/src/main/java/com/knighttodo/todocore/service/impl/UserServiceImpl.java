package com.knighttodo.todocore.service.impl;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.exception.UserNotFoundException;
import com.knighttodo.todocore.service.RoleService;
import com.knighttodo.todocore.service.UserService;
import com.knighttodo.todocore.gateway.privatedb.mapper.UserMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVO save(UserVO userVO) {
        RoleVO roleUser = roleService.findByName("ROLE_USER");
        userVO.setRoles(List.of(roleUser));
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        return userMapper.toUserVO(userRepository.save(userMapper.toUser(userVO)));
    }

    @Override
    public List<UserVO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserVO).collect(toList());
    }

    @Override
    public UserVO findById(UUID userId) {
        return userRepository.findById(userId).map(userMapper::toUserVO)
                .orElseThrow(() -> new UserNotFoundException(String.format("Can't find user with id: %s", userId)));
    }

    @Override
    public UserVO findByUsername(String username) {
        return userRepository.findByLogin(username).map(userMapper::toUserVO)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Can't find user with username: %s",
                        username)));
    }

    @Override
    public UserVO updateUser(UUID userId, UserVO userVO) {
        UserVO dbUser = findById(userId);
        dbUser.setLogin(userVO.getLogin());
        dbUser.setPassword(userVO.getPassword());
        return userMapper.toUserVO(userRepository.save(userMapper.toUser(userVO)));
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }
}
