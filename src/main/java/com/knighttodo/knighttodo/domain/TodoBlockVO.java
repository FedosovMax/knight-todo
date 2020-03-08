package com.knighttodo.knighttodo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoBlockVO {

    private long id;

    private String blockName;

    private List<TodoVO> todoList;
}
