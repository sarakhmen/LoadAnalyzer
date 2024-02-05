package com.sarakhman.loadanalyzer.strategy.parser.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathFieldParsingStrategyTest {
    private PathFieldParsingStrategy pathFieldParsingStrategy;

    @BeforeEach
    public void setUp() {
        pathFieldParsingStrategy = new PathFieldParsingStrategy();
    }

    @Test
    public void shouldSimplyReturnTheSameStringWhenParsingField() {
        String pathToParse = "/validPath";
        String parsedPath = pathFieldParsingStrategy.parseField(pathToParse);
        assertEquals(pathToParse, parsedPath);
    }
}