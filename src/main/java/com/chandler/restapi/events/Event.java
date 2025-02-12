package com.chandler.restapi.events;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.chandler.restapi.events.EventStatus.DRAFT;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue(strategy = IDENTITY)
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

    @Enumerated(value = STRING)
    private EventStatus eventStatus = DRAFT;

}
