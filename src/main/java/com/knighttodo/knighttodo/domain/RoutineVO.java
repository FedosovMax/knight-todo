package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

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
public class RoutineVO {

    private String id;

    private String name;

    private Hardness hardness;

    private Scariness scariness;

    private String templateId;

    private boolean ready;

    private BlockVO block;

    private List<TodoVO> todos = new ArrayList<>();
}
