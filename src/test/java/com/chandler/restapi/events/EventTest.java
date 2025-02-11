package com.chandler.restapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventTest {

    @Test
    @DisplayName("@Builder 존재합니다.")
    void builder() {
        Event event = Event.builder()
                .build();
        assertNotNull(event);
    }

    @Test
    @DisplayName("javaBean 규약 만족")
    void javaBean() {
        //given
        Event event = new Event();
        String name = "Event";
        String description = "Spring";
        event.setName(name);
        event.setDescription(description);

        assertEquals(event.getName(), name);
        assertEquals(event.getDescription(), description);
    }


}