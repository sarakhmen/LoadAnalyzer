package com.sarakhman.loadanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ReportDto{
    List<UriCallDto> mostFrequentUriCalls;
    Map<String, StatisticsParameter> statistics = new HashMap<>();
    Map<String, CountersParameter> counters = new HashMap<>();
}
