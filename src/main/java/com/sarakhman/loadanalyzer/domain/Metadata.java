package com.sarakhman.loadanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Metadata {
    private List<LineValidationResult> validationResults;
}
