package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.CardScannedEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.DeviceInitializationEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.PowerEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.TimeStampEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.session.DeviceSession;
import com.grundfos.futurelab.athariflowbox.athariiotservice.session.DeviceSessionService;
import com.grundfos.futurelab.athariflowbox.athariiotservice.session.SessionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/events")
public class EventController {
    private final EventService eventService;
    private final DeviceSessionService deviceSessionService;

    @PostMapping("/power")
    public ResponseEntity<ApiResponse<PowerEventDomain>> saveEvent(@RequestBody EventTypeDto dto) {
        ApiResponse<PowerEventDomain> apiResponse = new ApiResponse<>();
        Event event = eventService.createFromEventDto(dto);
        PowerEventDomain domain = PowerEventDomain.mapEntityToDomain(event);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(domain);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/initialization")
    public ResponseEntity<ApiResponse<DeviceInitializationEventDomain>> handleDeviceInitialization(@RequestBody EventTypeDto dto) {
        ApiResponse<DeviceInitializationEventDomain> apiResponse = new ApiResponse<>();
        Event event = eventService.createFromEventDto(dto);
        DeviceInitializationEventDomain domain = DeviceInitializationEventDomain.mapEntityToDomain(event);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(domain);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/card/scanned")
    public ResponseEntity<ApiResponse<CardScannedEventDomain>> handleCardScanned(@RequestBody EventTypeDto dto) {
        ApiResponse<CardScannedEventDomain> apiResponse = new ApiResponse<>();
        Event event = eventService.createFromEventDto(dto);
        CardScannedEventDomain domain = CardScannedEventDomain.mapEntityToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/start_fetch")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleStartFetch(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        Event event = eventService.createFromEventDto(dto);
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/stop_fetch")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleStopFetch(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/fetch/timeout")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleFetchTimeout(@RequestBody TimeoutEventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        // Device sends its serialNumber,
        Optional<DeviceSession> optionalDeviceSession = deviceSessionService.findDeviceWithActiveSessionBySessionId(dto.getSessionId());
        if (!optionalDeviceSession.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setData(null);
            apiResponse.setMessage("No active session was found for device.");

            return ResponseEntity.notFound().build();
        }

        DeviceSession session = optionalDeviceSession.get();
        session.setEndedAt(LocalDateTime.now());
        session.setStatus(SessionStatus.TIMEOUT.getStatus());
        session.setIsActive(Boolean.FALSE);
        deviceSessionService.saveSession(session);

        Event event = eventService.createFromTimeoutDto(dto);
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);

        apiResponse.setData(domain);
        apiResponse.setMessage("Session timeout recorded successfully!");
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/tamper")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleTamper(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/leakage")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleLeakage(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        Event event = eventService.createFromEventDto(dto);
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/battery_low")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleBatteryLow(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }
}
