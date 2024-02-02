package com.sarakhman.loadanalyzer.strategy.parser.impl;

import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("requestStatusParser")
public class RequestStatusFieldParsingStrategy implements FieldParsingStrategy<HttpStatus> {
    @Override
    public HttpStatus parseField(String fieldValue) {
        return HttpStatus.valueOf(Integer.parseInt(fieldValue));
    }
}
