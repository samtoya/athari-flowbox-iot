package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public Collection<Device> fetchAll() {
        return deviceRepository.findAll();
    }

    public Device createDeviceFromDto(DeviceDto dto) {
        Device device = new Device();
        BeanUtils.copyProperties(dto, device);
        device.setId(UUID.randomUUID().toString());
        return device;
    }

    public void deleteDeviceById(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public Optional<Device> findDeviceById(String deviceId) {
        return deviceRepository.findById(deviceId);
    }

    public Device updateDeviceFromDto(Device device, DeviceDto dto) {
        if (!dto.getName().isEmpty()) {
            device.setName(dto.getName());
        }
        if (!dto.getName().isEmpty()) {
            device.setSerialNumber(dto.getSerialNumber());
        }

        return deviceRepository.save(device);
    }

    public Optional<Device> findDeviceBySerialNumber(String serialNumber) {
        return deviceRepository.findFirstBySerialNumber(serialNumber);
    }
}
