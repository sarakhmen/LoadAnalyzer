package com.sarakhman.loadanalyzer.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CsvReaderServiceTest {
    @Test
    void shouldReadLinesAndSplitThem() throws IOException {
        String lineSeparator = ";";
        String csvContent = """
                field1;field2;field3
                value1;value2;value3
                value4;value5;value6""";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));
        CsvReaderService csvReaderService = new CsvReaderService(lineSeparator);

        List<List<String>> result = csvReaderService.readData(inputStream);

        assertEquals(List.of(
                        List.of("field1", "field2", "field3"),
                        List.of("value1", "value2", "value3"),
                        List.of("value4", "value5", "value6")),
                result);
    }
}
