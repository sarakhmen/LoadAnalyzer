package com.sarakhman.loadanalyzer.service;

public interface ReaderService<T, V> {
    V readData(T dataStream);
}
