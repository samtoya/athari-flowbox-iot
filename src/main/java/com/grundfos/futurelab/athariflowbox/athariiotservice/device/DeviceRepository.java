package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
}
