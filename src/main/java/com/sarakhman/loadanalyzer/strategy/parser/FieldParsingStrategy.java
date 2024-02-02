package com.sarakhman.loadanalyzer.strategy.parser;

public interface FieldParsingStrategy<T> {
    T parseField(String fieldValue);
}
