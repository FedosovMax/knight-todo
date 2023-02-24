package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {

}
