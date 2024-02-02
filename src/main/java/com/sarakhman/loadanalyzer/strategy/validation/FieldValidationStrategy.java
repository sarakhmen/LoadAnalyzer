package com.sarakhman.loadanalyzer.strategy.validation;

import com.sarakhman.loadanalyzer.domain.Message;

import java.util.Optional;
import java.util.function.Function;

public interface FieldValidationStrategy {
    Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator);
}
