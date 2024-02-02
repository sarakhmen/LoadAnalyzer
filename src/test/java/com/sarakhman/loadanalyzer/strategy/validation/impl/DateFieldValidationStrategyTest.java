package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class DateFieldValidationStrategyTest {
    private static final String VALID_DATE = "2024-01-31";
    private static final String INVALID_DATE = "2024-01-32";
    private static final String VALID_DATE_FORMAT = "dd/MM/yyyy:HH:mm:ssZ";

    @InjectMocks
    private DateFieldValidationStrategy dateFieldValidationStrategy;
    @Mock
    private DateTimeFormatter formatter;
    @Mock
    private Function<String, Message> mockMessageGenerator;

    private Message message = new Message();

    @BeforeEach
    void setUp() {
        dateFieldValidationStrategy = new DateFieldValidationStrategy(VALID_DATE_FORMAT, formatter);
        Mockito.lenient().when(mockMessageGenerator.apply(Mockito.anyString())).thenReturn(message);
    }

    @Test
    void shouldReturnNoMessagesWhenFieldIsValid() {
        Optional<Message> validate = dateFieldValidationStrategy.validate(VALID_DATE, mockMessageGenerator);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    void DateFieldValidation_NotOk() {
        Mockito.when(ZonedDateTime.parse(INVALID_DATE, formatter)).thenThrow(DateTimeParseException.class);
        Optional<Message> validate = dateFieldValidationStrategy.validate(INVALID_DATE, mockMessageGenerator);
        Assertions.assertFalse(validate.isEmpty());
        Assertions.assertEquals(validate.get(), message);
    }
}