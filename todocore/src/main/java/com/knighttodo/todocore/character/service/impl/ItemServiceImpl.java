package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.ItemVO;
import com.knighttodo.todocore.character.service.ItemService;
import com.knighttodo.todocore.character.service.privatedb.mapper.ItemMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemVO> findAll() {
        return itemRepository.findAll().stream().map(itemMapper::toItemVO).collect(Collectors.toList());
    }
}
