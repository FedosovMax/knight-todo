package com.knighttodo.character.gateway.privatedb.repository;

import com.knighttodo.character.gateway.privatedb.representation.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
