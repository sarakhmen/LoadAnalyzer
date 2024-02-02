package com.sarakhman.loadanalyzer.strategy.analyzer.impl;

import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.dto.RequestsPerSecondDto;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("requestsPerSecondStatisticsAnalyzer")
public class RequestsPerSecondStatisticsAnalyzerStrategy implements AnalyzerStrategy {
    @Value("${report.statistics.parameter.request-per-second}")
    private String requestsPerSecondParamName;
    @Value("${csv.record.field.date.name}")
    private String dateParamName;

    @Override
    public void analyzeData(List<Record> records, ReportDto report, Metadata metadata, Map<String, Object> inputParams) {
        List<RequestsPerSecondDto.DateFrequencyDto> dateFrequencies = records.stream()
                .map(record -> record.getField(dateParamName))
                .filter(ObjectUtils::isNotEmpty)
                .filter(ZonedDateTime.class::isInstance)
                .map(ZonedDateTime.class::cast)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new RequestsPerSecondDto.DateFrequencyDto(entry.getKey(), entry.getValue()))
                .toList();
        report.getStatistics().put(requestsPerSecondParamName, new RequestsPerSecondDto(dateFrequencies));
    }
}
