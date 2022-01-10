package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class DeviceDto {
    @NotNull
    private String name;
    @NotNull
    private String serialNumber;
    private String image;
}
