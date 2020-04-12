package com.knighttodo.knighttodo.rest.dto.todo.response;

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
public class TodoResponseDto {

    private String id;

    private String todoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private String todoBlockId;

    private Integer experience;
}
