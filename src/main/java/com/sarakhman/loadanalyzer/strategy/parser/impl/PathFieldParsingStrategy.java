package com.sarakhman.loadanalyzer.strategy.parser.impl;

import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.stereotype.Component;

@Component("pathParser")
public class PathFieldParsingStrategy implements FieldParsingStrategy<String> {
    @Override
    public String parseField(String fieldValue) {
        return fieldValue;
    }
}
