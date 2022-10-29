package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.UserVO;
import com.knighttodo.todocore.gateway.privatedb.mapper.UserMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.UserRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserVO save(UserVO userVO) {
        User user = userRepository.save(userMapper.toUser(userVO));
        return userMapper.toUserVO(user);
    }

    @Override
    public List<UserVO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserVO).collect(toList());
    }

    @Override
    public Optional<UserVO> findById(UUID userId) {
        return userRepository.findById(userId).map(userMapper::toUserVO);
    }

    @Override
    public Optional<UserVO> findByLogin(String login) {
        return userRepository.findByLogin(login).map(userMapper::toUserVO);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }
}
