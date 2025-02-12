package com.chandler.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.chandler.restapi.events.EventStatus.DRAFT;
import static com.chandler.restapi.events.EventStatus.PUBLISHED;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createEvent() throws Exception {
        var event = getEvent();

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION)) // Type-safe, instead of "Location"
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(Matchers.not(100)))
                .andExpect(jsonPath("$.free").value(Matchers.not(true)))
                .andExpect(jsonPath("$.eventStatus").value(DRAFT.name()))
        ;
    }

    private static Event getEvent() {
        var event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2025, 2, 11, 12, 12,12))
                .closeEnrollmentDateTime(LocalDateTime.of(2025, 2, 13, 12, 12,12))
                .beginEventDateTime(LocalDateTime.of(2025, 2, 14, 12, 12,12))
                .endEventDateTime(LocalDateTime.of(2025, 2, 15, 12, 12,12))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("잠실역")
                .free(true)
                .offline(false)
                .eventStatus(PUBLISHED)
                .build();
        return event;
    }

}