package com.knighttodo.knighttodo.domain;

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
public class TodoVO {

    private String id;

    private String todoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private TodoBlockVO todoBlockVO;

    private Integer experience;
}
