package com.chandler.restapi.domain;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(JUnitParamsRunner.class)
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

//    @CsvSource({
//            "0, 0, true",
//            "100, 0, false",
//            "0, 100, false"
//    }) // Type safe 하지 않음
    @ParameterizedTest
    @MethodSource(value = "parametersForTestFree") // "paramsForTestFree" 를 안쓰고도 적용되는지 체크
    @DisplayName("basePrice와 maxPrice가 둘다 0이면 무료입니다")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        //given
        Event event= Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    public static Object[] parametersForTestFree() {
        return new Object[]{
                new Object[]{0, 0, true},
                new Object[]{100, 0, false},
                new Object[]{0, 100, false},
        };
    }

    @ParameterizedTest
    @MethodSource(value = "parametersForTestOffline")
    @DisplayName("location 값이 존재하면 오프라인")
    public void testOffline(String location, boolean isOffline) {
        //given
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    public static Object[] parametersForTestOffline() {
        return new Object[]{
                new Object[]{"서울시 강남구", true},
                new Object[]{"        ", false},
                new Object[]{null, false},
        };
    }


}