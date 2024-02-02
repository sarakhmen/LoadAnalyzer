package com.sarakhman.loadanalyzer.controller;

import com.sarakhman.loadanalyzer.dto.ReportDto;
import com.sarakhman.loadanalyzer.service.ProcessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Tag(name = "Csv", description = "Csv file analyzer APIs")
@RestController
@RequestMapping("/csv")
public class CsvAnalyzerController {
    private final ProcessorService<ReportDto> processorService;
    private final String topEndpointsAmountParamName;

    @Autowired
    public CsvAnalyzerController(ProcessorService<ReportDto> processorService,
                                 @Value("${input.parameter.name.top-endpoints}") String topEndpointsAmountParamName) {
        this.processorService = processorService;
        this.topEndpointsAmountParamName = topEndpointsAmountParamName;
    }

    @Operation(
            summary = "Analyze a csv file",
            description = "Analyze a csv file using preconfigured validators, parsers and data analyzers. The response " +
                    "object's fields could be easily extended depending on applied analyzers"
    )
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReportDto> analyzeApiAccessLoad(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("topEndpointsAmount") @Valid @Min(0) Long topEndpointsAmount)
            throws IOException {
        return ResponseEntity.ok(processorService.processData(file.getInputStream(),
                createInputParametersMap(topEndpointsAmount)));
    }

    private Map<String, Object> createInputParametersMap(Long topEndpointsAmount) {
        return Map.of(topEndpointsAmountParamName, topEndpointsAmount);
    }
}
