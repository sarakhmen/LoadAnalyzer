package com.sarakhman.loadanalyzer.service;

public interface ParserService<T, V> {
    V parseData(T data);
}
