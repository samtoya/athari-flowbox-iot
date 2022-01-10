package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event createFromEventDto(EventTypeDto dto) {
        String data = new Gson().toJson(dto.getData());
        Event event = Event.builder()
                .id(UUID.randomUUID().toString())
                .serialNumber(dto.getSerialNumber())
                .type(dto.getType().getEvent())
                .data(data)
                .build();

        return eventRepository.save(event);
    }

    public Event createFromTimeoutDto(TimeoutEventTypeDto dto) {
        Map<String, String> map = dto.getData();
        map.put("session_id", dto.getSessionId());
        String data = new Gson().toJson(map);
        Event event = Event.builder()
                .id(UUID.randomUUID().toString())
                .serialNumber(dto.getSerialNumber())
                .type(dto.getType().getEvent())
                .data(data)
                .build();

        return eventRepository.save(event);
    }
}
