package com.chandler.restapi.response;

import com.chandler.restapi.controller.EventController;
import com.chandler.restapi.domain.Event;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends EntityModel<Event> {

    public EventResource(Event event) {
        super(event);
        add(linkTo(EventController.class).withRel("query-events"));
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
        add(linkTo(EventController.class).withRel("update-event"));
    }

}
