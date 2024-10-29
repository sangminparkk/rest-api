package com.chandler.restapi.controller;

import com.chandler.restapi.domain.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/events", produces = HAL_JSON_VALUE)
public class EventController {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
        URI createdUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(1L);
        return ResponseEntity.created(createdUri).body(event);
    }
}
