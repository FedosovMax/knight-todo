package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);
}
