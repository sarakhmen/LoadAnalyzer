package com.sarakhman.loadanalyzer.strategy.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RequestMethodFieldParsingStrategyTest {
    public static final String VALID_METHOD = "GET";
    public static final String INVALID_METHOD = "UNKNOWN";

    @InjectMocks
    private RequestMethodFieldParsingStrategy requestMethodFieldParsingStrategy;

    @Test
    public void shouldParseHttpMethodCorrectly() {
        HttpMethod parsedHttpMethod = requestMethodFieldParsingStrategy.parseField(VALID_METHOD);
        assertEquals(HttpMethod.GET, parsedHttpMethod);
    }

    @Test
    public void shouldThrowExceptionWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> requestMethodFieldParsingStrategy.parseField(null));
    }
}
