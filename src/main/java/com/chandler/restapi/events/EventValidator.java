package com.chandler.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) { //TODO Errors에 에러 정보를 담아주기
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            errors.rejectValue("basePrice", "Wrong value", "BasePrice is wrong");
            errors.rejectValue("maxPrice", "Wrong value", "MaxPrice is wrong");

            //TODO Global Error :  errors.reject
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "Wrong value", "EndEventDateTime is wrong");
        }

        //TODO BeginEnrollmentDateTime
        //TODO CloseEnrollmentDateTime

    }

}
