package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDomain {
    private String id;
    private String name;
    private String passcode;
    private String serialNumber;
    private String createdAt;

    public static DeviceDomain mapEntityToDomain(Device device) {
        DeviceDomain domain = new DeviceDomain();
        BeanUtils.copyProperties(device, domain);
        return domain;
    }
}
