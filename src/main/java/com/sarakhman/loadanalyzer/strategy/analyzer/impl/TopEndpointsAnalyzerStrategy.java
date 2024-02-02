package com.sarakhman.loadanalyzer.strategy.analyzer.impl;

import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.dto.UriCallDto;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("topEndpointsAnalyzer")
public class TopEndpointsAnalyzerStrategy implements AnalyzerStrategy {
    @Value("${csv.record.field.path.name}")
    private String pathFieldName;
    @Value("${csv.record.field.request-method.name}")
    private String requestMethodFieldName;
    @Value("${input.parameter.name.top-endpoints}")
    private String topEndpointsParameterName;

    @Override
    public void analyzeData(List<Record> records, ReportDto report, Metadata metadata, Map<String, Object> inputParams) {
        Long topEndpointsNumber = (Long) inputParams.get(topEndpointsParameterName);
        if (topEndpointsNumber != null) {
            report.setMostFrequentUriCalls(getMostFrequentEndpoints(records, topEndpointsNumber));
        }
    }

    protected List<UriCallDto> getMostFrequentEndpoints(List<Record> records, Long endpointNumber) {
        Map<Endpoint, Long> endpointFrequencyMap = records.stream()
                .collect(Collectors.groupingBy(record -> new Endpoint(record.getField(pathFieldName),
                                record.getField(requestMethodFieldName)),
                        Collectors.counting()));

        return endpointFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(endpointNumber)
                .map(entry -> new UriCallDto(entry.getKey().path(), entry.getKey().requestMethod(), entry.getValue()))
                .toList();
    }

    record Endpoint(String path, HttpMethod requestMethod) {
    }
}
