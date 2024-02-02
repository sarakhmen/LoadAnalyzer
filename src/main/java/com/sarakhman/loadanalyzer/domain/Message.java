package com.sarakhman.loadanalyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message  {
    private String fieldName;
    private String fieldValue;
    private String description;
}
