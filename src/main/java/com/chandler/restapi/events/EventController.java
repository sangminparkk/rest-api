package com.chandler.restapi.events;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/events", produces = HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        // 기본 바인딩 검증
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 비즈니스 로직 검증
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        var event = modelMapper.map(eventDto, Event.class);
        var newEvent = eventRepository.save(event);
        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

        return ResponseEntity.created(createdUri).body(event);
    }

}
