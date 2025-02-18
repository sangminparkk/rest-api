package com.chandler.restapi.events;

import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 1. RepresentationModel<EventModel> : @JsonUnwrapped 해줘야하는 번거로움 발생
 * 2. 명시적으로 Unwrapped 을 하지 않아도 됨
 */
@Getter
public class EventModel extends EntityModel<Event> {

    protected EventModel(Event event, Iterable<Link> links) {
        super(event, links);
    }

    protected EventModel(Event event) {
        super(event);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    protected EventModel() {
        super();
    }
}
