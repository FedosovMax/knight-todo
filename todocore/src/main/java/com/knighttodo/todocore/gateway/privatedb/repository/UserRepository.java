package com.knighttodo.todocore.gateway.privatedb.repository;

import com.knighttodo.todocore.gateway.privatedb.representation.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);
}
