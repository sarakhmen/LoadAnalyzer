package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateFieldValidationStrategyTest {
    private static final String VALID_DATE = "2024-01-31";
    private static final String INVALID_DATE = "2024-01-32";

    @InjectMocks
    private DateFieldValidationStrategy dateFieldValidationStrategy;
    @Mock
    private DateTimeFormatter formatter;
    private Message message = new Message();
    private Function<String, Message> messageGenerator = x -> message;;

    @Test
    public void shouldReturnNoMessagesWhenDateIsValid() {
        when(ZonedDateTime.parse(VALID_DATE, formatter)).thenAnswer((invocation) -> null);
        Optional<Message> validate = dateFieldValidationStrategy.validate(VALID_DATE, messageGenerator);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void shouldReturnValidationMessageWhenDateIsInvalid() {
        when(ZonedDateTime.parse(INVALID_DATE, formatter)).thenThrow(DateTimeParseException.class);
        Optional<Message> validate = dateFieldValidationStrategy.validate(INVALID_DATE, messageGenerator);
        assertFalse(validate.isEmpty());
        assertEquals(validate.get(), message);
    }
}