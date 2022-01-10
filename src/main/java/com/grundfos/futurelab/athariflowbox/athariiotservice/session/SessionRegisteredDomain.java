package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionRegisteredDomain {
    private String id;
    private String serialNumber;
    private String phoneNumber;
    private String sessionId;
    private String status;
    private Boolean isActive;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public static SessionRegisteredDomain mapEntityToDomain(DeviceSession session) {
        SessionRegisteredDomain domain = new SessionRegisteredDomain();
        BeanUtils.copyProperties(session, domain);
        return domain;
//        return SessionRegisteredDomain.builder()
//                .id(session.getId())
//                .serialNumber(session.getSerialNumber())
//                .phoneNumber(session.getPhoneNumber())
//                .sessionId(session.getSessionId())
//                .status(session.getStatus())
//                .isActive(session.getIsActive())
//                .startedAt(session.getCreatedAt())
//                .endedAt(session.getEndedAt())
//                .build();
    }
}
