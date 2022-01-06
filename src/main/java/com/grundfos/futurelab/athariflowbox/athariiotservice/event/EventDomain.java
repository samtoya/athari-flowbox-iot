package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventDomain<T> {
    private String id;
    private String type;
    private String serialNumber;
    private T data;
    private LocalDateTime createdAt;

}
