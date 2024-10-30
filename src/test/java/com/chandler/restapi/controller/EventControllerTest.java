package com.chandler.restapi.controller;

import com.chandler.restapi.domain.Event;
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

    @Test
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request() throws Exception {
        //given
        Event event = Event.builder()
                .id(100L)
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
                .offline(true)
                .free(true)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력 값이 비어 있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_With_Wrong_Input() throws Exception {
        //given
        EventDto eventDto = EventDto.builder()
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(eventDto))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Event")
                .description("Event with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024, 10, 29, 1, 2))
                .closeEnrollmentDateTime(LocalDateTime.of(2024, 10, 30, 1, 2))
                .beginEventDateTime(LocalDateTime.of(2024, 11, 25, 1, 2))
                .closeEventDateTime(LocalDateTime.of(2024, 11, 24, 1, 2)) // earlier than begin
                .basePrice(1000) // cannot be bigger than max
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("서울 OOO구 OOO센터")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(eventDto))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
        ;
    }

}