package com.sarakhman.loadanalyzer.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Record {
    private final Map<String, Object> fields = new LinkedHashMap<>();
    public void setField(String fieldName, Object fieldValue){
        fields.put(fieldName, fieldValue);
    }
    public <T> T getField(String fieldName){
        return (T) fields.get(fieldName);
    }
}
