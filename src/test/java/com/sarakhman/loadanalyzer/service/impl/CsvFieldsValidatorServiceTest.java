package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.provider.impl.BeanNameBasedStrategyProvider;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvFieldsValidatorServiceTest {
    @InjectMocks
    private CsvFieldsValidatorService validatorService;
    @Mock
    private BeanNameBasedStrategyProvider<FieldValidationStrategy> provider;
    @Mock
    private FieldValidationStrategy validationStrategy;
    @Spy
    private List<String> fieldNames = List.of("field1", "field2");

    private List<List<String>> data = List.of(List.of("value1", "value2"));

    @BeforeEach
    public void setUp() {
        when(provider.getStrategy(anyString())).thenReturn(validationStrategy);
        validatorService.afterPropertiesSet();
    }

    @Test
    public void shouldReturnValidationResultWithEmptyMessagesWhenFieldsAreValid() {
        when(validationStrategy.validate(anyString(), any())).thenReturn(Optional.empty());

        List<LineValidationResult> expected = List.of(new LineValidationResult(0L, true,
                Collections.emptyList()));
        List<LineValidationResult> actual = validatorService.validateData(data);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnValidationResultWithMessagesWhenFieldsAreInvalid() {
        Message message = new Message();
        when(validationStrategy.validate(anyString(), any())).thenReturn(Optional.of(message));

        List<LineValidationResult> expected = List.of(new LineValidationResult(0L, false,
                List.of(message, message)));
        List<LineValidationResult> actual = validatorService.validateData(data);

        assertEquals(expected, actual);
    }
}