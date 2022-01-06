package com.grundfos.futurelab.athariflowbox.athariiotservice.device;


import com.grundfos.futurelab.athariflowbox.athariiotservice.common.Auditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device extends Auditable {
    @Id
    private String id;
    private String name;
    private String passcode;
    private String serialNumber;
}
