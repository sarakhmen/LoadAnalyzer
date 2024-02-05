package com.sarakhman.loadanalyzer.strategy.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RequestStatusFieldParsingStrategyTest {
    public static final String OK_STATUS = "200";
    public static final String INVALID_STATUS = "2000";
    public static final String NON_NUMBER_STATUS = "nonNumber";

    @InjectMocks
    private RequestStatusFieldParsingStrategy requestStatusFieldParsingStrategy;

    @Test
    public void shouldParseHttpStatusCorrectly() {
        HttpStatus parsedHttpStatus = requestStatusFieldParsingStrategy.parseField(OK_STATUS);
        assertEquals(HttpStatus.OK, parsedHttpStatus);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenInvalidHttpStatus() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> requestStatusFieldParsingStrategy.parseField(INVALID_STATUS));
        assertEquals("No matching constant for [" + INVALID_STATUS + "]", exception.getMessage());
    }

    @Test
    public void shouldThrowNumberFormatExceptionWhenNonNumberOrNull() {
        assertThrows(NumberFormatException.class, () -> requestStatusFieldParsingStrategy.parseField(NON_NUMBER_STATUS));
        assertThrows(NumberFormatException.class, () -> requestStatusFieldParsingStrategy.parseField(null));
    }
}