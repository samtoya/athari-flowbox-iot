package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    Optional<Device> findFirstBySerialNumber(String serialNumber);
}
