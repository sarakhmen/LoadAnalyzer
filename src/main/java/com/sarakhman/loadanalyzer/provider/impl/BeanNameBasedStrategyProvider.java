package com.sarakhman.loadanalyzer.provider.impl;

import com.sarakhman.loadanalyzer.provider.StrategyProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanNameBasedStrategyProvider<T> implements StrategyProvider<T>, InitializingBean {
    private final String strategySuffix;
    private final Class<T> strategyClass;
    private final List<String> keys;
    private final ApplicationContext context;
    private Map<String, T> keyToStrategy;

    public BeanNameBasedStrategyProvider(String strategySuffix,
                                         Class<T> strategyClass,
                                         List<String> keys,
                                         ApplicationContext context) {
        this.strategySuffix = strategySuffix;
        this.strategyClass = strategyClass;
        this.keys = keys;
        this.context = context;
    }

    @Override
    public T getStrategy(String key) {
        return keyToStrategy.get(key);
    }

    @Override
    public void afterPropertiesSet() {
        keyToStrategy = keys.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        key -> context.getBean(key + strategySuffix, strategyClass)
                ));
    }
}
