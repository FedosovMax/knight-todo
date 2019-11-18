package com.knighttodo.knighttodo.rest.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {

    private long id;

    private String todoName;

    private Scaryness scaryness;

    private Hardness hardness;

    private boolean ready;

    private TodoBlock todoBlock;
}
