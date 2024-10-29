package com.chandler.restapi.controller;

import com.chandler.restapi.request.EventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.chandler.restapi.domain.EventStatus.DRAFT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("이벤트 생성 후 정상 응답 처리")
    public void createEvent() throws Exception {
        //given
        EventDto eventDto = EventDto.builder()
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

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Event"))
                .andExpect(jsonPath("$.description").value("Event with Spring"))
                .andExpect(jsonPath("$.offline").value(Matchers.not(true)))
                .andExpect(jsonPath("$.eventStatus").value(DRAFT.name()))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }

}