package com.sarakhman.loadanalyzer.strategy.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DateFieldParsingStrategyTest {
    public static final String VALID_DATE = "28/07/2006:10:27:10-0300";
    public static final String INVALID_DATE = "28-07-2006:10:27:10-0300";

    @InjectMocks
    private DateFieldParsingStrategy dateFieldParsingStrategy;
    @Spy
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ssZ");

    @Test
    public void shouldParseZonedDateTimeCorrectly() {
        ZonedDateTime parsedDate = dateFieldParsingStrategy.parseField(VALID_DATE);
        assertEquals("2006-07-28T10:27:10-03:00", parsedDate.toString());
    }

    @Test
    public void shouldThrowNpeExceptionWhenNull(){
        assertThrows(NullPointerException.class, () -> dateFieldParsingStrategy.parseField(null));
    }

    @Test
    public void shouldThrowDateTimeParseExceptionWhenInvalidDate(){
        assertThrows(DateTimeParseException.class, () -> dateFieldParsingStrategy.parseField(INVALID_DATE));
    }
}
