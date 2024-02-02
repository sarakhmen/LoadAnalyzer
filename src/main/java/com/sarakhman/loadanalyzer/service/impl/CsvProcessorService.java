package com.sarakhman.loadanalyzer.service.impl;

import com.sarakhman.loadanalyzer.domain.LineValidationResult;
import com.sarakhman.loadanalyzer.domain.Metadata;
import com.sarakhman.loadanalyzer.domain.Record;
import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.service.AnalyzerService;
import com.sarakhman.loadanalyzer.service.ParserService;
import com.sarakhman.loadanalyzer.service.ProcessorService;
import com.sarakhman.loadanalyzer.service.ReaderService;
import com.sarakhman.loadanalyzer.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
class CsvProcessorService implements ProcessorService<ReportDto> {
    private final ReaderService<InputStream, List<List<String>>> readerService;
    private final ValidatorService<List<List<String>>, List<LineValidationResult>> validatorService;
    private final ParserService<List<List<String>>, List<Record>> parserService;
    private final AnalyzerService<List<Record>, ReportDto> analyzerService;

    @Autowired
    public CsvProcessorService(ReaderService<InputStream, List<List<String>>> readerService,
                               ValidatorService<List<List<String>>, List<LineValidationResult>> validatorService,
                               ParserService<List<List<String>>, List<Record>> parserService,
                               AnalyzerService<List<Record>, ReportDto> analyzerService) {
        this.readerService = readerService;
        this.validatorService = validatorService;
        this.parserService = parserService;
        this.analyzerService = analyzerService;
    }

    @Override
    public ReportDto processData(InputStream data, Map<String, Object> inputParams) {
        List<List<String>> fieldLines = readerService.readData(data);
        List<LineValidationResult> validationResults = validatorService.validateData(fieldLines);
        List<List<String>> validaLines = validatorService.getValidData(fieldLines, validationResults);
        List<Record> records = parserService.parseData(validaLines);
        return analyzerService.analyzeData(records, new Metadata(validationResults), inputParams);
    }
}
