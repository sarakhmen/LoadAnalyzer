package com.sarakhman.loadanalyzer.config;

import com.sarakhman.loadanalyzer.provider.impl.BeanNameBasedStrategyProvider;
import com.sarakhman.loadanalyzer.strategy.analyzer.AnalyzerStrategy;
import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StrategyProviderConfig {
    @Bean(name = "validationStrategyProvider")
    public BeanNameBasedStrategyProvider<FieldValidationStrategy> getValidationStrategyProvider(
            @Value("${provider.field.validation.strategy.suffix}") String strategySuffix,
            @Value("#{'${csv.record.fields}'.split(',')}") List<String> fieldNames,
            @Autowired ApplicationContext context
    ) {
        return new BeanNameBasedStrategyProvider<>(strategySuffix, FieldValidationStrategy.class, fieldNames, context);
    }

    @Bean(name = "parsingStrategyProvider")
    public BeanNameBasedStrategyProvider<FieldParsingStrategy<?>> getParsingStrategyProvider(
            @Value("${provider.field.parsing.strategy.suffix}") String strategySuffix,
            @Value("#{'${csv.record.fields}'.split(',')}") List<String> fieldNames,
            @Autowired ApplicationContext context
    ) {
        return new BeanNameBasedStrategyProvider<>(strategySuffix,
                (Class<FieldParsingStrategy<?>>) (Class<?>) FieldParsingStrategy.class, fieldNames, context);
    }

    @Bean(name = "analyzerStrategyProvider")
    public BeanNameBasedStrategyProvider<AnalyzerStrategy> getAnalyzerStrategyProvider(
            @Value("${provider.field.analyzer.strategy.suffix}") String strategySuffix,
            @Value("#{'${record.analyzers}'.split(',')}") List<String> analyzers,
            @Autowired ApplicationContext context
    ) {
        return new BeanNameBasedStrategyProvider<>(strategySuffix,
                AnalyzerStrategy.class, analyzers, context);
    }
}
