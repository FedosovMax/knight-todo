package com.knighttodo.todocore.character.service;

import com.knighttodo.todocore.character.domain.ItemVO;

import java.util.List;

public interface ItemService {

    List<ItemVO> findAll();
}
