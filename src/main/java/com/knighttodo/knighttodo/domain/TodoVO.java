package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoVO {

    private long id;

    private String todoName;

    private Scaryness scaryness;

    private Hardness hardness;

    private boolean ready;

    private TodoBlock todoBlock;
}
