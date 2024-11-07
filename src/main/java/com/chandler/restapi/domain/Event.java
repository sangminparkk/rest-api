package com.chandler.restapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String location; // on/off meeting

    private boolean offline;

    private boolean free;

    private int limitOfEnrollment;

    private int basePrice;

    private int maxPrice;

    @Enumerated(value = STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime closeEventDateTime;

    @ManyToOne(fetch = LAZY)
    private Account account;

    public void update() {
        if (this.maxPrice == 0 && this.basePrice == 0) {
            this.free = true;
        } else {
            this.free = false;
        }

        if (this.location == null || this.location.isBlank()) { // empty를 커버한다
            this.offline = false;
        } else {
            this.offline = true;
        }
    }
}
