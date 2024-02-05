package com.sarakhman.loadanalyzer.strategy.parser.impl;

import com.sarakhman.loadanalyzer.strategy.parser.FieldParsingStrategy;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;

@Component("ipParser")
public class IpFieldParsingStrategy implements FieldParsingStrategy<Inet4Address> {
    @Override
    public Inet4Address parseField(String fieldValue) {
        try {
            return (Inet4Address) Inet4Address.getByName(fieldValue);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected ip address parsing exception.", ex);
        }
    }
}
