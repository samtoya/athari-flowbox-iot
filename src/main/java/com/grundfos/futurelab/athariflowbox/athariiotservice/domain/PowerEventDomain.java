package com.grundfos.futurelab.athariflowbox.athariiotservice.domain;

import com.grundfos.futurelab.athariflowbox.athariiotservice.event.Event;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PowerEventDomain {
    private LocalDateTime timestamp;
    private DeviceRegistrationDetailsDomain device;
    private BigDecimal pricePerLitre;

    public static PowerEventDomain mapEntityToDomain(Event event) {
        DeviceRegistrationDetailsDomain device = DeviceRegistrationDetailsDomain.builder()
                .serialNumber(event.getSerialNumber())
                .build();

        return PowerEventDomain.builder()
                .timestamp(event.getCreatedAt())
                .pricePerLitre(new BigDecimal(40))
                .device(device)
                .build();
    }
}
