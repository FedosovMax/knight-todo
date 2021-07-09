package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineRepository extends JpaRepository<Routine, UUID> {

}
