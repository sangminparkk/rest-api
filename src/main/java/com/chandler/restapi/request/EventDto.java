package com.chandler.restapi.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {

    private String name;

    private String description;

    private String location;

    private int limitOfEnrollment;

    private int basePrice;

    private int maxPrice;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime closeEventDateTime;
}
