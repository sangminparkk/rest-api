package com.chandler.restapi.controller;

import com.chandler.restapi.domain.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EventController {

    @PostMapping("/api/events")
    public ResponseEntity createEvent(@RequestBody Event event) throws JsonProcessingException {
        URI createdUri = linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri();
        event.setId(1L);
        return ResponseEntity.created(createdUri).body(event);
    }
}
