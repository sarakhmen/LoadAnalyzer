package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;

@Component("dateValidator")
public class DateFieldValidationStrategy implements FieldValidationStrategy {
    private static final String INVALID_DATE_MESSAGE = "Provided date doesn't conform format '%s' format.";

    private final DateTimeFormatter formatter;

    public DateFieldValidationStrategy(@Autowired DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator) {
        return isDateValid(field) ? Optional.empty() :
                Optional.of(invalidFieldMessageGenerator.apply(
                        String.format(INVALID_DATE_MESSAGE, formatter.toFormat()))
                );
    }

    private boolean isDateValid(String date) {
        try {
            ZonedDateTime.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
