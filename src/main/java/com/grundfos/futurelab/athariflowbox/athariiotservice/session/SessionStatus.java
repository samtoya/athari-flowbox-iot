package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SessionStatus {
    ACTIVE("active"),
    TIMEOUT("timeout"),
    COMPLETED("completed");

    private final String status;
}
