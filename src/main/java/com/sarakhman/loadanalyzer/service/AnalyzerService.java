package com.sarakhman.loadanalyzer.service;

import com.sarakhman.loadanalyzer.domain.Metadata;

import java.util.Map;

public interface AnalyzerService<T, V> {
    V analyzeData(T data, Metadata metadata, Map<String, Object> inputParams);
}
