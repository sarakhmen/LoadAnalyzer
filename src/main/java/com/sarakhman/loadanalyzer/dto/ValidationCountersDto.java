package com.sarakhman.loadanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ValidationCountersDto implements CountersParameter {
    private long totalRows;
    private long validRows;
    private long processedTotalTimeSeconds;
    private List<ValidationErrorDto> validationErrors;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ValidationErrorDto{
        long lineNumber;
        List<String> messages;
    }
}
