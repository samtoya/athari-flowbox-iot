package com.grundfos.futurelab.athariflowbox.athariiotservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceRegistrationDetailsDomain {
    private String name;
    private String serialNumber;
}
