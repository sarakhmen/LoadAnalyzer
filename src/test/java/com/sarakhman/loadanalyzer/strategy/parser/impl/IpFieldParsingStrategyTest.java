package com.sarakhman.loadanalyzer.strategy.parser.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.Inet4Address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class IpFieldParsingStrategyTest {
    private static final String VALID_IP = "192.168.1.1";
    private static final String INVALID_IP = "invalidIp";

    @InjectMocks
    private IpFieldParsingStrategy ipFieldParsingStrategy;

    @Test
    public void shouldParseInet4AddressCorrectly() {
        Inet4Address parsedIpAddress = ipFieldParsingStrategy.parseField(VALID_IP);
        assertEquals("192.168.1.1", parsedIpAddress.getHostAddress());
    }

    @Test
    public void shouldThrowExceptionWhenInvalidIpAddress() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ipFieldParsingStrategy.parseField(INVALID_IP));
        assertEquals("Unexpected ip address parsing exception.", exception.getMessage());
    }
}
