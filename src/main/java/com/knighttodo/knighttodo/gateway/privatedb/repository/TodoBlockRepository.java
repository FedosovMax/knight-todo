package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoBlockRepository extends JpaRepository<TodoBlock, Long> {

}
