package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineInstanceRepository extends JpaRepository<RoutineInstance, UUID> {

}
