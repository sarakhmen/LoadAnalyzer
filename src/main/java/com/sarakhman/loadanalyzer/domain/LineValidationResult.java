package com.sarakhman.loadanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LineValidationResult {
    private Long lineNumber;
    private Boolean isValid;
    private List<Message> messages;
}
