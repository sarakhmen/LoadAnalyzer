package com.sarakhman.loadanalyzer.service;

import java.io.InputStream;
import java.util.Map;

public interface ProcessorService<T> {
    T processData(InputStream data, Map<String, Object> inputParams);
}
