package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.ItemVO;

import java.util.List;

public interface ItemGateway {

    List<ItemVO> findAll();
}
