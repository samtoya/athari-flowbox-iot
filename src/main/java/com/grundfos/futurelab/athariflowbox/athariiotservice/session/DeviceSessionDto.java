package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class DeviceSessionDto {
    @NotNull
    private String serialNumber;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String sessionId;
}
