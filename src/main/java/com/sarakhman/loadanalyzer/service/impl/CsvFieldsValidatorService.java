package com.sarakhman.loadanalyzer.service.impl;

import com.google.common.collect.Streams;
import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.provider.StrategyProvider;
import com.sarakhman.loadanalyzer.service.ValidatorService;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@Service
public class CsvFieldsValidatorService implements ValidatorService<List<List<String>>, List<LineValidationResult>>,
        InitializingBean {
    private static final String LINE_PARSING_EXCEPTION_MESSAGE = "The number of fields of the provided line doesn't " +
            "correspond to the number of configured expected fields.";

    private final List<String> fieldNames;
    private final StrategyProvider<FieldValidationStrategy> validationStrategyProvider;
    private List<FieldValidationStrategy> orderedValidationStrategies;

    @Autowired
    public CsvFieldsValidatorService(@Value("#{'${csv.record.fields}'.split(',')}") List<String> fieldNames,
                                     @Qualifier("validationStrategyProvider")
                                     StrategyProvider<FieldValidationStrategy> validationStrategyProvider) {
        this.fieldNames = fieldNames;
        this.validationStrategyProvider = validationStrategyProvider;
    }

    @Override
    public void afterPropertiesSet() {
        orderedValidationStrategies = fieldNames.stream()
                .map(validationStrategyProvider::getStrategy)
                .toList();
    }

    @Override
    public List<List<String>> getValidData(List<List<String>> data, List<LineValidationResult> validationResult) {
        List<Long> invalidLines = validationResult.stream()
                .filter(result -> !result.getIsValid())
                .map(LineValidationResult::getLineNumber)
                .toList();
        return LongStream.range(0, data.size())
                .filter(index -> !invalidLines.contains(index))
                .mapToObj(index -> data.get((int) index))
                .toList();
    }

    @Override
    public List<LineValidationResult> validateData(List<List<String>> data) {
        return Streams.mapWithIndex(data.stream(), this::validateRow)
                .toList();
    }

    protected LineValidationResult validateRow(List<String> fieldValues, Long lineNumber) {
        if (fieldNames.size() != fieldValues.size()) {
            return new LineValidationResult(lineNumber, false, List.of(new Message("entireLine",
                    "entireLine", LINE_PARSING_EXCEPTION_MESSAGE)));
        }

        List<Message> validationMessages = Streams.mapWithIndex(orderedValidationStrategies.stream(), (strategy, index) ->
                        validateField(strategy, fieldNames.get((int) index), fieldValues.get((int) index)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return new LineValidationResult(lineNumber, validationMessages.isEmpty(), validationMessages);
    }

    protected Optional<Message> validateField(FieldValidationStrategy validationStrategy, String fieldName,
                                              String fieldValue) {
        return validationStrategy.validate(fieldValue, description -> new Message(fieldName, fieldValue, description));
    }
}
