package com.sarakhman.loadanalyzer.strategy.parser.impl;

import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component("dateParser")
public class DateFieldParsingStrategy implements FieldParsingStrategy<ZonedDateTime> {
    private final DateTimeFormatter formatter;

    public DateFieldParsingStrategy(@Autowired DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public ZonedDateTime parseField(String fieldValue) {
        return ZonedDateTime.parse(fieldValue, formatter);
    }
}
