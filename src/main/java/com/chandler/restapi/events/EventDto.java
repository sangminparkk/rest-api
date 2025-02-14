package com.chandler.restapi.events;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 입력 가능한 데이터들만
 */
@Builder @AllArgsConstructor  @NoArgsConstructor
@Getter
public class EventDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime beginEnrollmentDateTime;

    @NotNull
    private LocalDateTime closeEnrollmentDateTime;

    @NotNull
    private LocalDateTime beginEventDateTime;

    @NotNull
    private LocalDateTime endEventDateTime;

    private String location;

    @Min(0)
    private int basePrice;

    @Min(0)
    private int maxPrice;

    @Min(0)
    private int limitOfEnrollment;

}
