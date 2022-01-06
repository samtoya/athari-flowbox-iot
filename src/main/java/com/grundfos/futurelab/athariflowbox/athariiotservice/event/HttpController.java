package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.CardScannedEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.DeviceInitializationEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.PowerEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.domain.TimeStampEventDomain;
import com.grundfos.futurelab.athariflowbox.athariiotservice.dtos.EventTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class HttpController {
    private final EventService eventService;

    @PostMapping("/power")
    public ResponseEntity<ApiResponse<PowerEventDomain>> saveEvent(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        PowerEventDomain domain = PowerEventDomain.mapEntityToDomain(event);
        ApiResponse<PowerEventDomain> apiResponse = new ApiResponse<>();
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
        Event event = eventService.createFromEventDto(dto);
        CardScannedEventDomain domain = CardScannedEventDomain.mapEntityToDomain(event);
        ApiResponse<CardScannedEventDomain> apiResponse = new ApiResponse<>();
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/start_fetch")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleStartFetch(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/stop_fetch")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleStopFetch(@RequestBody EventTypeDto dto) {
        Event event = eventService.createFromEventDto(dto);
        TimeStampEventDomain domain = TimeStampEventDomain.mapEventToDomain(event);
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/fetch/timeout")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleFetchTimeout(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tamper")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleTamper(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leakage")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleLeakage(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<TimeStampEventDomain>();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/battery_low")
    public ResponseEntity<ApiResponse<TimeStampEventDomain>> handleBatteryLow(@RequestBody EventTypeDto dto) {
        ApiResponse<TimeStampEventDomain> apiResponse = new ApiResponse<>();
        return ResponseEntity.ok().build();
    }
}
