package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoBlockRepository extends JpaRepository<TodoBlock, Long> {

    Optional<TodoBlock> findById(String blockId);

    void deleteById(String blockId);
}
