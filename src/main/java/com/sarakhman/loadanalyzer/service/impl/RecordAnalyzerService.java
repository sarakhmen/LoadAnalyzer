package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.provider.StrategyProvider;
import com.sarakhman.loadanalyzer.service.AnalyzerService;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecordAnalyzerService implements AnalyzerService<List<Record>, ReportDto> {
    private final List<String> analyzers;
    private final StrategyProvider<AnalyzerStrategy> analyzerStrategyProvider;

    @Autowired
    public RecordAnalyzerService(@Value("#{'${record.analyzers}'.split(',')}") List<String> analyzers,
                                 @Qualifier("analyzerStrategyProvider")
                                 StrategyProvider<AnalyzerStrategy> analyzerStrategyProvider) {
        this.analyzers = analyzers;
        this.analyzerStrategyProvider = analyzerStrategyProvider;
    }

    @Override
    public ReportDto analyzeData(List<Record> data, Metadata metadata, Map<String, Object> inputParams) {
        ReportDto report = new ReportDto();
        analyzers.forEach(analyzerName -> analyzerStrategyProvider.getStrategy(analyzerName)
                .analyzeData(data, report, metadata, inputParams));
        return report;
    }
}
