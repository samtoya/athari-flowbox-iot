package com.grundfos.futurelab.athariflowbox.athariiotservice.session;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import com.grundfos.futurelab.athariflowbox.athariiotservice.device.Device;
import com.grundfos.futurelab.athariflowbox.athariiotservice.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/sessions/")
public class DeviceSessionController {
    private final DeviceService deviceService;
    private final DeviceSessionService deviceSessionService;

    @GetMapping("/{serialNumber}")
    public ResponseEntity<ApiResponse<Collection<DeviceSessionDomain>>> getAllSessionsForDevice(@PathVariable("serialNumber") String serialNumber) {
        Collection<DeviceSessionDomain> domains = deviceSessionService.getAllBySerialNumber(serialNumber).stream().map(DeviceSessionDomain::mapEntityToDomain).collect(Collectors.toList());
        ApiResponse<Collection<DeviceSessionDomain>> apiResponse = new ApiResponse<>();
        apiResponse.setData(domains);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{serialNumber}/active")
    public ResponseEntity<ApiResponse<DeviceSessionDomain>> getActiveSessionBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        ApiResponse<DeviceSessionDomain> apiResponse = new ApiResponse<>();
        Optional<DeviceSession> optionalDeviceSession = deviceSessionService.findDeviceWithActiveSession(serialNumber);
        if (!optionalDeviceSession.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage("No active session for device");

            return ResponseEntity.notFound().build();
        }

        DeviceSession session = optionalDeviceSession.get();
        DeviceSessionDomain domain = DeviceSessionDomain.mapEntityToDomain(session);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(domain);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SessionRegisteredDomain>> createNewSession(@RequestBody @Valid DeviceSessionDto dto) {
        ApiResponse<SessionRegisteredDomain> apiResponse = new ApiResponse<>();
        // Check if a device with that serialNumber exists
        Optional<Device> optionalDevice = deviceService.findDeviceBySerialNumber(dto.getSerialNumber());
        // If device does not exist
        if (!optionalDevice.isPresent()) {
            // Return a message saying `Could not find device with the provided serial number`.
            apiResponse.setMessage("Could not find device with the provided serial number");
            apiResponse.setSuccess(Boolean.FALSE);

            // Return a message with unprocessable entity.
            return ResponseEntity.unprocessableEntity().body(apiResponse);
        }

        Device device = optionalDevice.get();
        Optional<DeviceSession> optionalDeviceSession = deviceSessionService.findDeviceWithActiveSession(dto.getSerialNumber());
        // If device with serialNumber already has an active session.
        if (optionalDeviceSession.isPresent()) {
            // Return a message saying `Device is already in session with a user, please try after some few minutes`
            apiResponse.setMessage("Device is already in session with a user, please try after some few minutes");
            apiResponse.setSuccess(Boolean.FALSE);

            return ResponseEntity.unprocessableEntity().body(apiResponse);
        }

        // Register the new session for the current request.
        DeviceSession session = DeviceSession.mapDtoToEntity(dto);
        deviceSessionService.saveSession(session);
        SessionRegisteredDomain domain = SessionRegisteredDomain.mapEntityToDomain(session);
        // Find a way to notify the hardware with the serialNumber domain data
        // TODO: Use a message broker to send this event to the hardware (MQTT)


        // Return a message for session with device started successfully.
        apiResponse.setMessage("Session was successfully registered for device");
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(domain);

        return ResponseEntity.ok(apiResponse);
    }

}
