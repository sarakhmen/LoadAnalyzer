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
class IpFieldValidationStrategyTest {
    private static final String VALID_IP_ADDRESS = "192.168.2.212";
    private static final String INVALID_IP_ADDRESS = "0.168.2.212";
    @InjectMocks
    private IpFieldValidationStrategy ipFieldValidationStrategy;
    private Message message = new Message();
    private Function<String, Message> messageGenerator  = x -> message;;

    @Test
    public void shouldReturnNoMessagesWhenIpIsValid() {
        Optional<Message> validate = ipFieldValidationStrategy.validate(VALID_IP_ADDRESS, messageGenerator);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void shouldReturnValidationMessageWhenIpIsInvalid() {
        Optional<Message> validate = ipFieldValidationStrategy.validate(INVALID_IP_ADDRESS, messageGenerator);
        assertFalse(validate.isEmpty());
        assertEquals(validate.get(), message);
    }
}