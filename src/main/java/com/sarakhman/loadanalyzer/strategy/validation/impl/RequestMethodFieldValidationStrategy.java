package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Component("requestMethodValidator")
public class RequestMethodFieldValidationStrategy implements FieldValidationStrategy {
    private static final String INVALID_REQUEST_METHOD_MESSAGE = "Provided request method doesn't exist.";

    @Override
    public Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator) {
        return isRequestMethodValid(field) ? Optional.empty() :
                Optional.of(invalidFieldMessageGenerator.apply(INVALID_REQUEST_METHOD_MESSAGE));
    }

    private boolean isRequestMethodValid(String inputRequestMethod){
        return Arrays.stream(HttpMethod.values())
                .map(HttpMethod::name)
                .anyMatch(method -> method.equals(inputRequestMethod));
    }
}
