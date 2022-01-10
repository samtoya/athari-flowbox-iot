package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class TimeoutEventTypeDto {
    @NotNull
    private DeviceEvent type;
    @NotNull
    private String sessionId;
    @NotNull
    private String serialNumber;
    @NotNull
    private Map<String, String> data;
}
