package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DayRepository extends JpaRepository<Day, UUID> {

}
