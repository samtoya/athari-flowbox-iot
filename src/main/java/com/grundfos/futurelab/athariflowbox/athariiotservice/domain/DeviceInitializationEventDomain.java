package com.grundfos.futurelab.athariflowbox.athariiotservice.domain;

import com.grundfos.futurelab.athariflowbox.athariiotservice.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.Random;

@Data
@Builder
public class DeviceInitializationEventDomain {
    private String code;

    public static DeviceInitializationEventDomain mapEntityToDomain(Event event) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String code = String.format("%06d", number);

        return DeviceInitializationEventDomain.builder()
                .code(code)
                .build();
    }
}
