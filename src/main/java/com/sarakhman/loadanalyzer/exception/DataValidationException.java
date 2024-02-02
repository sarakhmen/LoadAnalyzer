package com.sarakhman.loadanalyzer.exception;

import com.sarakhman.loadanalyzer.domain.LineValidationResult;

import java.util.List;

public class DataValidationException extends IllegalArgumentException {
    private static final String DEFAULT_EXCEPTION_MESSAGE = "Error during parsing the line.";
    private final List<LineValidationResult> validationResults;

    public DataValidationException(List<LineValidationResult> validationResults){
        this(validationResults, DEFAULT_EXCEPTION_MESSAGE);
    }

    public DataValidationException(List<LineValidationResult> validationResults, String message){
        super(message);
        this.validationResults = validationResults;
    }

    public DataValidationException(List<LineValidationResult> validationResults, Throwable throwable){
        this(validationResults, DEFAULT_EXCEPTION_MESSAGE, throwable);
    }

    public DataValidationException(List<LineValidationResult> validationResults, String message, Throwable throwable){
        super(message, throwable);
        this.validationResults = validationResults;
    }

    public List<LineValidationResult> getParsingResult() {
        return validationResults;
    }
}
