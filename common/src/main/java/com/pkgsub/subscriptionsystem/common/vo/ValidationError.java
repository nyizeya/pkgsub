package com.pkgsub.subscriptionsystem.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationError {
    @JsonIgnore
    private String object;

    @JsonIgnore
    private Object rejectedValue;

    private String field;
    private String message;
}