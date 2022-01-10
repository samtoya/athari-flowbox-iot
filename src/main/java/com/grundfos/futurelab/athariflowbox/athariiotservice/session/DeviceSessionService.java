package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceSessionService {
    private final DeviceSessionRepository deviceSessionRepository;

    Collection<DeviceSession> getAllBySerialNumber(String serialNumber) {
        return deviceSessionRepository.findAllBySerialNumber(serialNumber);
    }

    public Optional<DeviceSession> findDeviceWithActiveSession(String serialNumber) {
        return deviceSessionRepository.findFirstBySerialNumber(serialNumber);
    }

    public void saveSession(DeviceSession session) {
        deviceSessionRepository.saveAndFlush(session);
    }

    public Optional<DeviceSession> findDeviceWithActiveSessionBySessionId(String sessionId) {
        return deviceSessionRepository.findFirstBySessionIdAndIsActiveIsTrue(sessionId);
    }

    public DeviceSession findSessionById(String id) {
        return deviceSessionRepository.getById(id);
    }
}
