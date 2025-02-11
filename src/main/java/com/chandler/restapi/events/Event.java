package com.chandler.restapi.events;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Event {

    private Integer id;
    private String name;
    private String description;

    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;

    private String location; // null = offline
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;

    private Boolean offline;
    private Boolean free;
    private EventStatus eventStatus;

}
