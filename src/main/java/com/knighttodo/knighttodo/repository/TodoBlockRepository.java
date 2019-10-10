package com.knighttodo.knighttodo.repository;

import com.knighttodo.knighttodo.entity.TodoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoBlockRepository extends JpaRepository<TodoBlock, Long> {
}
