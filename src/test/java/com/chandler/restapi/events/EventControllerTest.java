package com.chandler.restapi.events;

import com.chandler.restapi.config.TestDescription;
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
    @TestDescription("정상적인 테스트 수행 - 비지니스 로직 추가")
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
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
                .build();

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
                .andExpect(jsonPath("$.free").value(false))
                .andExpect(jsonPath("$.offline").value(true))
                .andExpect(jsonPath("$.eventStatus").value(DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
        ;
    }

    @Test
    @TestDescription("입력 받을 수 없는 값을 사용한 경우 400 response")
    void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .id(100)
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

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력값이 비어 있는 경우 400 response")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
                .accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력값이 잘못된 경우 400 response")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2025, 2, 22, 12, 12,12))
                .closeEnrollmentDateTime(LocalDateTime.of(2025, 2, 21, 12, 12,12))
                .beginEventDateTime(LocalDateTime.of(2025, 2, 20, 12, 12,12))
                .endEventDateTime(LocalDateTime.of(2025, 2, 18, 12,12))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("잠실역")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].field").exists()) //TODO field error가 없는 경우에는 해당 테스트가 깨지니까 이 부분 보강 필요
                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;
    }

}