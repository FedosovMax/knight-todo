package com.knighttodo.todocore.character.service.privatedb.repository;

import com.knighttodo.todocore.character.service.privatedb.representation.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
