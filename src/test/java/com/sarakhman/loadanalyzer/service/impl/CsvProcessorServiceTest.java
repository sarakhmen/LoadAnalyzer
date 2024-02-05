package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.service.AnalyzerService;
import com.sarakhman.loadanalyzer.service.ParserService;
import com.sarakhman.loadanalyzer.service.ReaderService;
import com.sarakhman.loadanalyzer.service.ValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvProcessorServiceTest {
    @Mock
    private ReaderService<InputStream, List<List<String>>> mockReaderService;
    @Mock
    private ValidatorService<List<List<String>>, List<LineValidationResult>> mockValidatorService;
    @Mock
    private ParserService<List<List<String>>, List<Record>> mockParserService;
    @Mock
    private AnalyzerService<List<Record>, ReportDto> mockAnalyzerService;
    @Mock
    private InputStream mockInputStream;
    @InjectMocks
    private CsvProcessorService csvProcessorService;

    @Test
    void shouldExecuteProcessingFlow() {
        Map<String, Object> mockInputParams = Collections.emptyMap();
        List<List<String>> mockFieldLines = Collections.singletonList(Collections.singletonList("value1"));

        when(mockReaderService.readData(mockInputStream)).thenReturn(mockFieldLines);
        when(mockValidatorService.validateData(mockFieldLines)).thenReturn(Collections.emptyList());
        when(mockValidatorService.getValidData(mockFieldLines, Collections.emptyList())).thenReturn(mockFieldLines);
        when(mockParserService.parseData(mockFieldLines)).thenReturn(Collections.singletonList(new Record()));
        when(mockAnalyzerService.analyzeData(any(), any(), any())).thenReturn(new ReportDto());

        csvProcessorService.processData(mockInputStream, mockInputParams);

        verify(mockReaderService).readData(mockInputStream);
        verify(mockValidatorService).validateData(mockFieldLines);
        verify(mockValidatorService).getValidData(mockFieldLines, Collections.emptyList());
        verify(mockParserService).parseData(mockFieldLines);
        verify(mockAnalyzerService).analyzeData(any(), any(), any());
    }
}
