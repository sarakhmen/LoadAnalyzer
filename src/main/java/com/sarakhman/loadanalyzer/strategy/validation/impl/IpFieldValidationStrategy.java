package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Optional;
import java.util.function.Function;

@Component("ipValidator")
public class IpFieldValidationStrategy implements FieldValidationStrategy {
    private static final String INVALID_IP_ADDRESS_MESSAGE = "Provided IP address isn't a valid IPv4 address.";

    @Override
    public Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator) {
        return isIPv4(field) ? Optional.empty() :
                Optional.of(invalidFieldMessageGenerator.apply(INVALID_IP_ADDRESS_MESSAGE));
    }

    private static boolean isIPv4(String input) {
        try {
            InetAddress inetAddress = Inet4Address.getByName(input);
            return (inetAddress instanceof Inet4Address)
                    && inetAddress.getHostAddress().equals(input)
                    && !input.split("\\.")[0].startsWith("0");
        } catch (Exception ex) {
            return false;
        }
    }
}
