package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.provider.StrategyProvider;
import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordParserServiceTest {
    @InjectMocks
    private RecordParserService recordParserService;
    @Mock
    private StrategyProvider<FieldParsingStrategy<?>> parsingStrategyProvider;
    @Spy
    private List<String> fieldNames = List.of("field1", "field2", "field3");

    @Test
    void shouldParseDataCorrectly() {
        List<List<String>> testData = Arrays.asList(
                Arrays.asList("value1", "value2", "value3"),
                Arrays.asList("value4", "value5", "value6")
        );

        FieldParsingStrategy mockParsingStrategy1 = mock(FieldParsingStrategy.class);
        FieldParsingStrategy mockParsingStrategy2 = mock(FieldParsingStrategy.class);
        FieldParsingStrategy mockParsingStrategy3 = mock(FieldParsingStrategy.class);

        when(parsingStrategyProvider.getStrategy(fieldNames.get(0))).thenReturn(mockParsingStrategy1);
        when(parsingStrategyProvider.getStrategy(fieldNames.get(1))).thenReturn(mockParsingStrategy2);
        when(parsingStrategyProvider.getStrategy(fieldNames.get(2))).thenReturn(mockParsingStrategy3);

        List<Record> result = recordParserService.parseData(testData);

        verify(mockParsingStrategy1).parseField(eq("value1"));
        verify(mockParsingStrategy2).parseField(eq("value2"));
        verify(mockParsingStrategy3).parseField(eq("value3"));
        verify(mockParsingStrategy1).parseField(eq("value4"));
        verify(mockParsingStrategy2).parseField(eq("value5"));
        verify(mockParsingStrategy3).parseField(eq("value6"));

        assertEquals(2, result.size());
    }
}
