package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.provider.impl.BeanNameBasedStrategyProvider;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordAnalyzerServiceTest {
    @InjectMocks
    private RecordAnalyzerService analyzerService;
    @Mock
    private BeanNameBasedStrategyProvider<AnalyzerStrategy> provider;
    @Mock
    private AnalyzerStrategy analyzerStrategy;
    @Spy
    private List<String> analyzers = List.of("analyzer1", "analyzer2");
    private Metadata metadata = new Metadata(List.of(new LineValidationResult(0L, true, Collections.emptyList())));
    private Map<String, Object> inputParams = Collections.emptyMap();
    private List<Record> data = List.of(new Record());

    @BeforeEach
    public void setUp(){
        when(provider.getStrategy(anyString())).thenReturn(analyzerStrategy);
    }

    @Test
    void shouldCallAllConfiguredAnalyzers() {
        analyzerService.analyzeData(data, metadata, inputParams);
        verify(provider, times(1)).getStrategy(eq("analyzer1"));
        verify(provider, times(1)).getStrategy(eq("analyzer2"));
    }
}