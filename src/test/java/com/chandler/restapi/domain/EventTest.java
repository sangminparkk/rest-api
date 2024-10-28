package com.chandler.restapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @DisplayName("@Builder 적용되었는지")
    @Test
    public void builder() {
        Event event = Event.builder()
                .name("REST API")
                .description("development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @DisplayName("자바 빈 스펙을 만족하는가")
    @Test
    public void javaBean() {
        // given
        String name = "Event";
        String description = "Description";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
        assertNotNull(event);
    }


}