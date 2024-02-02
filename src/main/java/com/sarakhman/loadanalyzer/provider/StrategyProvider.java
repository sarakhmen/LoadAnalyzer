package com.sarakhman.loadanalyzer.provider;

public interface StrategyProvider<T> {
    T getStrategy(String key);
}
