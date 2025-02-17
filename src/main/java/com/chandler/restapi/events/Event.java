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

    public void update() {
        if (this.basePrice == 0 && this.maxPrice == 0) {
            this.free = true;
        } else {
            this.free = false;
        }

        if (this.location == null || this.location.isBlank()) { //TODO string 비어 있는지 체크하는 방법(컴팩트)
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
