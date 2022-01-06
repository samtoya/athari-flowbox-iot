package com.grundfos.futurelab.athariflowbox.athariiotservice.card;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDisableDto {
    private String reason;
}
