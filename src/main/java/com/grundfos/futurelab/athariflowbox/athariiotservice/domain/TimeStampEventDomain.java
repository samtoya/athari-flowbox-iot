package com.grundfos.futurelab.athariflowbox.athariiotservice.domain;

import com.grundfos.futurelab.athariflowbox.athariiotservice.event.Event;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TimeStampEventDomain {
    private LocalDateTime timestamp;

    public static TimeStampEventDomain mapEventToDomain(Event event) {
        return TimeStampEventDomain.builder()
                .timestamp(event.getCreatedAt())
                .build();
    }
}
