package com.chandler.restapi.response;

import com.chandler.restapi.domain.Event;
import org.springframework.hateoas.EntityModel;

public class EventResource extends EntityModel<Event> {

    public EventResource(Event content) {
        super(content);
    }

}
