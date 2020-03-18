package com.knighttodo.knighttodo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoBlockVO {

    private String id;

    private String blockName;
}
