package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvReaderService implements ReaderService<InputStream, List<List<String>>> {
    private final String fieldSeparator;

    @Autowired
    public CsvReaderService(@Value("${csv.record.fields.separator}") String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    @Override
    public List<List<String>> readData(InputStream dataStream) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(dataStream, StandardCharsets.UTF_8))) {
            return reader.lines()
                    .map(this::splitLine)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<String> splitLine(String line) {
        return List.of(line.split(fieldSeparator));
    }
}
