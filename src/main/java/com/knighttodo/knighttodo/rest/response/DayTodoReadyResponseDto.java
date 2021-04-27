package com.knighttodo.knighttodo.rest.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayTodoReadyResponseDto {

    private String id;

    private String dayTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private String dayId;

    private int experience;
}
