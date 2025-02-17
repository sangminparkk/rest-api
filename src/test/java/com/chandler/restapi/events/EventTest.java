package com.chandler.restapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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


    //TODO Unit test
    @ParameterizedTest
    @MethodSource("parametersForTestFree")
    void testFree(int basePrice, int maxPrice, boolean isFree) {
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        assertEquals(event.getFree(), isFree);
    }

    public static Stream<Object[]> parametersForTestFree() {
        return Stream.of(
                new Object[]{0, 0, true},
                new Object[]{100, 0, false},
                new Object[]{0, 100, false},
                new Object[]{100, 200, false}
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForTestOffline")
    void testOffline(String location, boolean isOffline) {
        //given
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertEquals(event.getOffline(), isOffline);
    }

    public static Stream<Object[]> parametersForTestOffline() {
        return Stream.of(
                new Object[]{"강남역 D2", true},
                new Object[]{null, false},
                new Object[]{"       ", false}
        );
    }

}