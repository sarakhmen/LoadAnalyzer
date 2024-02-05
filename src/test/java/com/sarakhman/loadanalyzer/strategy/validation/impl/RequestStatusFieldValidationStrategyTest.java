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
class RequestStatusFieldValidationStrategyTest {
    private static final String VALID_REQUEST_STATUS = "200";
    private static final String INVALID_REQUEST_STATUS = "2000";

    @InjectMocks
    private RequestStatusFieldValidationStrategy requestStatusFieldValidationStrategy;
    private Message message = new Message();
    private Function<String, Message> messageGenerator = x -> message;

    @Test
    public void shouldReturnNoMessagesWhenRequestStatusIsValid() {
        Optional<Message> validate = requestStatusFieldValidationStrategy.validate(VALID_REQUEST_STATUS, messageGenerator);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void shouldReturnValidationMessageWhenRequestStatusIsInvalid() {
        Optional<Message> validate = requestStatusFieldValidationStrategy.validate(INVALID_REQUEST_STATUS, messageGenerator);
        assertFalse(validate.isEmpty());
        assertEquals(validate.get(), message);
    }
}