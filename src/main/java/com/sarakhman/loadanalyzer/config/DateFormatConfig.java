package com.sarakhman.loadanalyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateFormatConfig {
    @Value("${csv.record.field.date.format}")
    private String formatPattern;

    @Bean
    public SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        dateFormat.setLenient(false);
        return dateFormat;
    }

    @Bean
    public DateTimeFormatter getDateTimeFormatter(){
        return DateTimeFormatter.ofPattern(formatPattern);
    }
}
