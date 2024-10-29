package com.chandler.restapi.controller;

import com.chandler.restapi.domain.Event;
import com.chandler.restapi.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 생성 후 json 데이터 응답")
    public void createEvent_with_json() throws Exception {
        //given
        Event event = Event.builder()
                .name("Event")
                .description("Event with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 10, 29, 1, 2))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 10, 30, 1, 2))
                .beginEventDateTime(LocalDateTime.of(2024, 11, 25, 1, 2))
                .closeEventDateTime(LocalDateTime.of(2024, 11, 26, 1, 2))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("서울 OOO구 OOO센터")
                .build();

        event.setId(1L);
        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Event"))
                .andExpect(jsonPath("$.description").value("Event with Spring"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }

}