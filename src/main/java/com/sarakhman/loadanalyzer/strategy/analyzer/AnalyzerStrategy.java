package com.sarakhman.loadanalyzer.strategy.analyzer;

import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;

import java.util.List;
import java.util.Map;

public interface AnalyzerStrategy {
    void analyzeData(List<Record> records, ReportDto report, Metadata metadata, Map<String, Object> inputParams);
}
