package com.knighttodo.knighttodo.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockVO {

    private String id;

    private String blockName;

    private List<RoutineVO> routines = new ArrayList<>();

    private List<TodoVO> todos = new ArrayList<>();
}
