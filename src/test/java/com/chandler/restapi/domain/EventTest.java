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

    @Test
    @DisplayName("basePrice와 maxPrice가 둘다 0이면 무료입니다")
    public void testFree() {
        //given
        Event event1 = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        //when
        event1.update();

        //then
        assertThat(event1.isFree()).isTrue();

        //given
        Event event2 = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        //when
        event2.update();

        //then
        assertThat(event2.isFree()).isFalse();

        //given
        Event event3 = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        //when
        event3.update();

        //then
        assertThat(event3.isFree()).isFalse();
    }

    @Test
    @DisplayName("location 값이 존재하면 오프라인")
    public void testOffline() {
        //given
        Event event = Event.builder()
                .location("서울시 강남구")
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isTrue();

        //given
        Event event1 = Event.builder()
                .location("")
                .build();

        //when
        event1.update();

        //then
        assertThat(event1.isOffline()).isFalse();

        //given
        Event event2 = Event.builder()
                .location(null)
                .build();

        //when
        event2.update();

        //then
        assertThat(event2.isOffline()).isFalse();

    }

}