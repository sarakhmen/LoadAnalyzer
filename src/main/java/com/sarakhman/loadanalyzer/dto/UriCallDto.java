package com.sarakhman.loadanalyzer.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sarakhman.loadanalyzer.serializer.HttpMethodSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Setter
@Getter
@AllArgsConstructor
public class UriCallDto {
    String path;
    @JsonSerialize(using = HttpMethodSerializer.class) HttpMethod httpMethod;
    long times;
}
