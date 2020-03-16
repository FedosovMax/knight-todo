package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TodoBlockRepository extends JpaRepository<TodoBlock, Long> {

    Optional<TodoBlock> findById(String blockId);

    @Transactional
    void deleteById(String blockId);
}
