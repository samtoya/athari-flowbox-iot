package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface DeviceSessionRepository extends JpaRepository<DeviceSession, String> {
    Collection<DeviceSession> findAllBySerialNumber(String serialNumber);

    Optional<DeviceSession> findFirstBySerialNumber(String serialNumber);

    Optional<DeviceSession> findFirstBySessionIdAndIsActiveIsTrue(String sessionId);
}
