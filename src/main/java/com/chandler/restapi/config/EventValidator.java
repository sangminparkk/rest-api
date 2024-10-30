package com.chandler.restapi.config;

import com.chandler.restapi.request.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator  {

    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
            errors.rejectValue("basePrice" ,"wrongValue", "BasePrice is wrong.");
            errors.rejectValue("maxPrice" ,"wrongValue", "maxPrice is wrong.");
        }

        LocalDateTime closeEventDateTime = eventDto.getCloseEventDateTime();
        if (closeEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                closeEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                closeEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())
        ) {
            errors.rejectValue("closeEventDateTime" ,"wrongValue", "closeEventDateTime is wrong.");
        }

        //TODO: BeginEventDateTime
        //TODO: CloseEnrollmentDateTime
    }

}
