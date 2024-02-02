package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component("pathValidator")
public class PathFieldValidationStrategy implements FieldValidationStrategy {
    private static final String INVALID_PATH_MESSAGE = "Provided path is invalid.";
    private static final String PATH_PATTERN = "^/(?:[a-zA-Z0-9_-]+/)*[a-zA-Z0-9_-]*$";

    @Override
    public Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator) {
        return isPathValid(field) ? Optional.empty() :
                Optional.of(invalidFieldMessageGenerator.apply(INVALID_PATH_MESSAGE));
    }

    private boolean isPathValid(String path){
        return path.matches(PATH_PATTERN);
    }
}
