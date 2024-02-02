package com.sarakhman.loadanalyzer.service;

public interface ValidatorService<T, V> {
    V validateData(T data);

    T getValidData(T data, V validationResult);
}
