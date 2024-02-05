package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RequestMethodFieldValidationStrategyTest {
    private static final String VALID_REQUEST_METHOD = "POST";
    private static final String INVALID_REQUEST_METHOD = "UNKNOWN";

    @InjectMocks
    private RequestMethodFieldValidationStrategy requestMethodFieldValidationStrategy;
    private Message message = new Message();
    private Function<String, Message> messageGenerator = x -> message;

    @Test
    public void shouldReturnNoMessagesWhenRequestMethodIsValid() {
        Optional<Message> validate = requestMethodFieldValidationStrategy.validate(VALID_REQUEST_METHOD, messageGenerator);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void shouldReturnValidationMessageWhenRequestMethodIsInvalid() {
        Optional<Message> validate = requestMethodFieldValidationStrategy.validate(INVALID_REQUEST_METHOD, messageGenerator);
        assertFalse(validate.isEmpty());
        assertEquals(validate.get(), message);
    }
}