package com.chandler.restapi.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private String location;

    @Min(0)
    private int limitOfEnrollment;

    @Min(0)
    private int basePrice;

    @Min(0)
    private int maxPrice;

    @NotNull
    private LocalDateTime beginEnrollmentDateTime;

    @NotNull
    private LocalDateTime closeEnrollmentDateTime;

    @NotNull
    private LocalDateTime beginEventDateTime;

    @NotNull
    private LocalDateTime closeEventDateTime;
}
