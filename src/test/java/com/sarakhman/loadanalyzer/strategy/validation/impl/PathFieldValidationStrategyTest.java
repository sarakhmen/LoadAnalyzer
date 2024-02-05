package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class PathFieldValidationStrategyTest {
    private static final String VALID_PATH = "/your/path/to/validate";
    private static final String PATH_WITHOUT_SLASH_AT_THE_BEGGING = "invalidPath";
    private static final String PATH_WITH_TWO_CONSECUTIVE_SLASHES = "/invalidPath//";
    private static final String PATH_WITH_UNACCEPTABLE_SYMBOL = "/invalidPath/$";

    @InjectMocks
    private PathFieldValidationStrategy pathFieldValidationStrategy;
    private Message message = new Message();
    private Function<String, Message> messageGenerator = x -> message;

    @Test
    public void shouldReturnNoMessagesWhenPathIsValid() {
        Optional<Message> validate = pathFieldValidationStrategy.validate(VALID_PATH, messageGenerator);
        Assertions.assertTrue(validate.isEmpty());
    }

    @Test
    public void shouldReturnValidationMessageWhenPathDoesNotStartWithSlash() {
        Optional<Message> validate = pathFieldValidationStrategy.validate(PATH_WITHOUT_SLASH_AT_THE_BEGGING, messageGenerator);
        Assertions.assertFalse(validate.isEmpty());
        Assertions.assertEquals(validate.get(), message);
    }

    @Test
    public void shouldReturnValidationMessageWhenPathContainsTwoConsecutiveSlashes() {
        Optional<Message> validate = pathFieldValidationStrategy.validate(PATH_WITH_TWO_CONSECUTIVE_SLASHES, messageGenerator);
        Assertions.assertFalse(validate.isEmpty());
        Assertions.assertEquals(validate.get(), message);
    }

    @Test
    public void shouldReturnValidationMessageWhenPathContainsUnacceptableSymbol() {
        Optional<Message> validate = pathFieldValidationStrategy.validate(PATH_WITH_UNACCEPTABLE_SYMBOL, messageGenerator);
        Assertions.assertFalse(validate.isEmpty());
        Assertions.assertEquals(validate.get(), message);
    }
}