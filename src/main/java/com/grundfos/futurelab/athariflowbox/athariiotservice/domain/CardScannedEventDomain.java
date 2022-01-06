package com.grundfos.futurelab.athariflowbox.athariiotservice.domain;

import com.grundfos.futurelab.athariflowbox.athariiotservice.event.Event;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CardScannedEventDomain {
    private BigDecimal pricePerLitre;
    private LocalDateTime timestamp;
    private AccountInfoDomain account;

    public static CardScannedEventDomain mapEntityToDomain(Event event) {
        AccountInfoDomain account = AccountInfoDomain.builder()
                .balance(new BigDecimal("500.0"))
                .name("Felix Biego")
                .threshold(40)
                .build();

        return CardScannedEventDomain.builder()
                .pricePerLitre(new BigDecimal("5.86"))
                .timestamp(event.getCreatedAt())
                .account(account)
                .build();
    }
}
