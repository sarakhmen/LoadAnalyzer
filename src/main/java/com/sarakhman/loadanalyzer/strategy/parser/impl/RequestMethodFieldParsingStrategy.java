package com.sarakhman.loadanalyzer.strategy.parser.impl;

import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component("requestMethodParser")
public class RequestMethodFieldParsingStrategy implements FieldParsingStrategy<HttpMethod> {
    @Override
    public HttpMethod parseField(String fieldValue) {
        return HttpMethod.valueOf(fieldValue);
    }
}
