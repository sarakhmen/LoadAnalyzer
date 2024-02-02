package com.sarakhman.loadanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class RequestsPerSecondDto implements StatisticsParameter {
    private List<DateFrequencyDto> result;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class DateFrequencyDto {
        private ZonedDateTime date;
        private long frequency;
    }
}
