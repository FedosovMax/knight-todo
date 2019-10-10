package com.knighttodo.knighttodo.repository;

import com.knighttodo.knighttodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
