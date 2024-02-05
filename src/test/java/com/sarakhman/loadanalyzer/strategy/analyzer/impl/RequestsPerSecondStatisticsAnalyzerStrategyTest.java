package com.sarakhman.loadanalyzer.strategy.analyzer.impl;

import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.dto.RequestsPerSecondDto;
import com.sarakhman.loadanalyzer.dto.StatisticsParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.sarakhman.loadanalyzer.dto.RequestsPerSecondDto.DateFrequencyDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestsPerSecondStatisticsAnalyzerStrategyTest {
    public static final String REQUEST_PER_SECOND_PARAM_NAME = "requestsPerSecond";
    public static final String DATE_PARAM_NAME = "date";
    @Mock
    private Metadata metadata;
    private ReportDto report;

    private RequestsPerSecondStatisticsAnalyzerStrategy requestsPerSecondAnalyzerStrategy;

    @BeforeEach
    void setUp() {
        requestsPerSecondAnalyzerStrategy = new RequestsPerSecondStatisticsAnalyzerStrategy();
        ReflectionTestUtils.setField(requestsPerSecondAnalyzerStrategy, "requestsPerSecondParamName", REQUEST_PER_SECOND_PARAM_NAME);
        ReflectionTestUtils.setField(requestsPerSecondAnalyzerStrategy, "dateParamName", DATE_PARAM_NAME);
        HashMap<String, StatisticsParameter> statistics = spy(new HashMap<>());
        report = spy(new ReportDto());
        report.setStatistics(statistics);
    }

    @Test
    void shouldPopulateReportWithRequestsPerSecondStatistics() {
        ZonedDateTime mockDate1 = ZonedDateTime.now().minusHours(1);
        ZonedDateTime mockDate2 = ZonedDateTime.now().minusHours(2);
        List<Record> mockRecords = List.of(
                createMockRecord(mockDate1),
                createMockRecord(mockDate1),
                createMockRecord(mockDate2));

        requestsPerSecondAnalyzerStrategy.analyzeData(mockRecords, report, metadata, Collections.emptyMap());

        mockRecords.forEach(record -> verify(record).getField(DATE_PARAM_NAME));
        verify(report).getStatistics();
        verify(report.getStatistics()).put(eq(REQUEST_PER_SECOND_PARAM_NAME), any(RequestsPerSecondDto.class));

        RequestsPerSecondDto requestsPerSecondDto = (RequestsPerSecondDto) report.getStatistics().get(REQUEST_PER_SECOND_PARAM_NAME);
        assertEquals(2, requestsPerSecondDto.getResult().size());
        List<DateFrequencyDto> sortedDateFrequencies = requestsPerSecondDto.getResult().stream()
                .sorted(Comparator.comparing(DateFrequencyDto::getDate))
                .toList();
        assertEquals(mockDate2, sortedDateFrequencies.get(0).getDate());
        assertEquals(1, sortedDateFrequencies.get(0).getFrequency());
        assertEquals(mockDate1, sortedDateFrequencies.get(1).getDate());
        assertEquals(2, sortedDateFrequencies.get(1).getFrequency());
    }

    private Record createMockRecord(ZonedDateTime date) {
        Record mockRecord = mock(Record.class);
        when(mockRecord.getField(DATE_PARAM_NAME)).thenReturn(date);
        return mockRecord;
    }
}
