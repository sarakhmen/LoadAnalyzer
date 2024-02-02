package com.sarakhman.loadanalyzer.service.impl;

import com.google.common.collect.Streams;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.provider.StrategyProvider;
import com.sarakhman.loadanalyzer.service.ParserService;
import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordParserService implements ParserService<List<List<String>>, List<Record>> {
    private final List<String> fieldNames;
    private final StrategyProvider<FieldParsingStrategy<?>> parsingStrategyProvider;

    @Autowired
    public RecordParserService(@Value("#{'${csv.record.fields}'.split(',')}") List<String> fieldNames,
                               @Qualifier("parsingStrategyProvider")
                               StrategyProvider<FieldParsingStrategy<?>> parsingStrategyProvider) {
        this.fieldNames = fieldNames;
        this.parsingStrategyProvider = parsingStrategyProvider;
    }

    @Override
    public List<Record> parseData(List<List<String>> data) {
        return data.stream()
                .map(this::parseRow)
                .toList();
    }

    protected Record parseRow(List<String> row){
        Record record = new Record();
        Streams.forEachPair(fieldNames.stream(), row.stream(),
                (fieldName, fieldValue) -> setRecordField(record, fieldName, fieldValue));
        return record;
    }

    protected void setRecordField(Record record, String fieldName, String fieldValue){
        FieldParsingStrategy<?> strategy = parsingStrategyProvider.getStrategy(fieldName);
        record.setField(fieldName, strategy.parseField(fieldValue));
    }
}
