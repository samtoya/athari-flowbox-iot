package com.grundfos.futurelab.athariflowbox.athariiotservice.device;


import com.grundfos.futurelab.athariflowbox.athariiotservice.common.Auditable;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device extends Auditable {
    @Id
    private String id;
    private String customerId;
    private String name;
    private String passcode;
    @Column(unique = true, nullable = false)
    private String serialNumber;
    private LocalDateTime pairedAt;

    public static Device fromDto(DeviceDto dto) {
        Device device = new Device();
        BeanUtils.copyProperties(dto, device);
        device.setId(UUID.randomUUID().toString());
        return device;
    }
}
