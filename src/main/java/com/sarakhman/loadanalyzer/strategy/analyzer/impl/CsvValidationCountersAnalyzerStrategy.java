package com.sarakhman.loadanalyzer.strategy.analyzer.impl;

import com.sarakhman.loadanalyzer.aop.ExecutionTimeSecondsThreadLocal;
import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.dto.ValidationCountersDto;
import com.sarakhman.loadanalyzer.dto.ValidationCountersDto.ValidationErrorDto;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("csvValidationCountersAnalyzer")
public class CsvValidationCountersAnalyzerStrategy implements AnalyzerStrategy {
    private static final String ERROR_MESSAGE_FORMAT = "Field name = %s, field value = %s, details = %s";

    @Value("${report.counters.parameter.csv-validation.name}")
    private String validationCountersParamName;

    @Override
    public void analyzeData(List<Record> records, ReportDto report, Metadata metadata, Map<String, Object> inputParams) {
        List<LineValidationResult> validationResults = metadata.getValidationResults();
        long totalRows = validationResults.size();
        long validRows = validationResults.stream()
                .filter(LineValidationResult::getIsValid)
                .count();
        report.getCounters().put(validationCountersParamName,
                new ValidationCountersDto(totalRows, validRows, ExecutionTimeSecondsThreadLocal.get(),
                        getValidationErrors(validationResults)));
    }

    protected List<ValidationErrorDto> getValidationErrors(List<LineValidationResult> validationResults) {
        return validationResults.stream()
                .filter(result -> !result.getIsValid())
                .map(invalidResult -> new ValidationErrorDto(invalidResult.getLineNumber() + 1,
                        invalidResult.getMessages().stream()
                                .map(message -> String.format(ERROR_MESSAGE_FORMAT, message.getFieldName(),
                                        message.getFieldValue(), message.getDescription()))
                                .toList()))
                .toList();
    }
}
