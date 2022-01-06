package com.grundfos.futurelab.athariflowbox.athariiotservice.event;

import com.google.gson.Gson;
import com.grundfos.futurelab.athariflowbox.athariiotservice.dtos.EventTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .type(dto.getType())
                .data(data)
                .build();

        return eventRepository.save(event);
    }
}
