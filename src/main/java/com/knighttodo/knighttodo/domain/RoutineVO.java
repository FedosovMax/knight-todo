package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;

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

    private Scaryness scaryness;

    private String templateId;

    private TodoBlockVO todoBlock;

    private List<TodoVO> todos = new ArrayList<>();
}
