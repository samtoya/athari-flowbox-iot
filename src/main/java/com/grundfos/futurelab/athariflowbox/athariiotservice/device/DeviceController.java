package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponse<Collection<DeviceDomain>>> getAllDevices() {
        ApiResponse<Collection<DeviceDomain>> apiResponse = new ApiResponse<>();
        List<DeviceDomain> domains = deviceService.fetchAll().stream().map(DeviceDomain::mapEntityToDomain)
                .collect(Collectors.toList());
        apiResponse.setData(domains);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeviceDomain>> createDevice(@RequestBody DeviceDto dto) {
        ApiResponse<DeviceDomain> apiResponse = new ApiResponse<>();
        try {
            Device device = deviceService.createDeviceFromDto(dto);
            DeviceDomain domain = DeviceDomain.mapEntityToDomain(device);
            apiResponse.setSuccess(Boolean.TRUE);
            apiResponse.setData(domain);
            URI uri = MvcUriComponentsBuilder.fromController(getClass())
                    .path("/{deviceId}")
                    .buildAndExpand(device.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setData(null);

            return ResponseEntity.internalServerError()
                    .body(apiResponse);
        }
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<DeviceDomain>> getDeviceById(@PathVariable("deviceId") String deviceId) {
        ApiResponse<DeviceDomain> apiResponse = new ApiResponse<>();
        Optional<Device> optionalDevice = deviceService.findDeviceById(deviceId);
        if (!optionalDevice.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage("Device not found with id: " + deviceId);
            return ResponseEntity.notFound().build();
        }

        Device device = optionalDevice.get();
        DeviceDomain domain = DeviceDomain.mapEntityToDomain(device);
        apiResponse.setMessage(null);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<DeviceDomain>> updateDevice(@PathVariable("deviceId") String deviceId,
                                                                  @RequestBody DeviceDto dto) {
        Optional<Device> optionalDevice = deviceService.findDeviceById(deviceId);
        ApiResponse<DeviceDomain> apiResponse = new ApiResponse<>();
        if (!optionalDevice.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage("Device not found with id: " + deviceId);
            return ResponseEntity.notFound().build();
        }

        Device device = optionalDevice.get();
        device = deviceService.updateDeviceFromDto(device, dto);
        DeviceDomain domain = DeviceDomain.mapEntityToDomain(device);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setMessage(null);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable("deviceId") String deviceId) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        try {
            deviceService.deleteDeviceById(deviceId);
            apiResponse.setSuccess(Boolean.TRUE);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setSuccess(Boolean.FALSE);

            return ResponseEntity.internalServerError().body(apiResponse);
        }
    }

}
