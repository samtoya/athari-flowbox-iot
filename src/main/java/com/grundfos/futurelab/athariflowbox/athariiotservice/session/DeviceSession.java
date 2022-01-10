package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.Auditable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "device_sessions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSession extends Auditable {
    @Id
    private String id;
    private String serialNumber;
    private String phoneNumber;
    private String sessionId;
    private String status;
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    private Boolean isActive;

    public static DeviceSession mapDtoToEntity(DeviceSessionDto dto) {
        return DeviceSession.builder()
                .id(UUID.randomUUID().toString())
                .sessionId(dto.getSessionId())
                .isActive(Boolean.TRUE)
                .phoneNumber(dto.getPhoneNumber())
                .serialNumber(dto.getSerialNumber())
                .status(SessionStatus.ACTIVE.getStatus())
                .build();
    }


}
