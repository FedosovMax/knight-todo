package com.knighttodo.knighttodo.rest.response;

import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayTodoResponseDto {

    private UUID id;

    private String dayTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private UUID dayId;
}
