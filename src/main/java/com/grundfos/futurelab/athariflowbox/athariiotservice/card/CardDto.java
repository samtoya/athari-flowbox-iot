package com.grundfos.futurelab.athariflowbox.athariiotservice.card;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CardDto {
    private String serialNumber;
    private String holder;
    private Boolean isActive;
    private LocalDateTime disabledAt;
    private String reason;
}
