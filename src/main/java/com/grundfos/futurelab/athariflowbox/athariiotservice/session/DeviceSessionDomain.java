package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSessionDomain {

    private String id;
    private String serialNumber;
    private String phoneNumber;
    private String sessionId;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Boolean isActive;

    public static DeviceSessionDomain mapEntityToDomain(DeviceSession deviceSession) {
        DeviceSessionDomain domain = new DeviceSessionDomain();
        BeanUtils.copyProperties(deviceSession, domain);
        return domain;
    }
}
