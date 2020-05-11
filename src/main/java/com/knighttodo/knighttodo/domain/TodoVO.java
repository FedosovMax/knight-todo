package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TodoVO {

    private String id;

    private String todoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private BlockVO block;

    private RoutineVO routine;

    private Integer experience;
}
