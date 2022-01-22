package com.grundfos.futurelab.athariflowbox.athariiotservice.device;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Device", description = "Device Management")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    @Operation(summary = "Get all devices")
    public ResponseEntity<ApiResponse<Collection<DeviceDomain>>> getAllDevices() {
        ApiResponse<Collection<DeviceDomain>> apiResponse = new ApiResponse<>();
        List<DeviceDomain> domains = deviceService.fetchAll().stream().map(DeviceDomain::mapEntityToDomain)
                .collect(Collectors.toList());
        apiResponse.setData(domains);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @Operation(summary = "Create a new device")
    public ResponseEntity<ApiResponse<DeviceDomain>> createDevice(@RequestBody DeviceDto dto) {
        ApiResponse<DeviceDomain> apiResponse = new ApiResponse<>();
        Device device = deviceService.createDeviceFromDto(dto);
        DeviceDomain domain = DeviceDomain.mapEntityToDomain(device);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(domain);
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{deviceId}")
                .buildAndExpand(device.getId())
                .toUri();

        return ResponseEntity.created(uri).body(apiResponse);
    }

    @GetMapping("/{deviceId}")
    @Operation(summary = "Get a device by id")
    public ResponseEntity<ApiResponse<DeviceDomain>> getDeviceById(@PathVariable("deviceId") String deviceId) {
        Optional<Device> optionalDevice = deviceService.findDeviceById(deviceId);
        ApiResponse<DeviceDomain> apiResponse = new ApiResponse<>();
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
    @Operation(summary = "Update a device")
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
    @Operation(summary = "Delete a device")
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

    @GetMapping("/{deviceId}/passcode")
    @Operation(summary = "Get passcode for a device ")
    public ResponseEntity<ApiResponse<String>> getDevicePasscode(@PathVariable("deviceId") String deviceId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        Optional<Device> optionalDevice = deviceService.findDeviceById(deviceId);
        if (!optionalDevice.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage("Device not found with id: " + deviceId);
            return ResponseEntity.notFound().build();
        }

        Device device = optionalDevice.get();
        String passcode = device.getPasscode();
        apiResponse.setData(passcode);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setMessage(null);

        return ResponseEntity.ok(apiResponse);
    }
}
