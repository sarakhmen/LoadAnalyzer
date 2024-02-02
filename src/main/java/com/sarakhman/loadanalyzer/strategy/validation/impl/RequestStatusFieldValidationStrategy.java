package com.sarakhman.loadanalyzer.strategy.validation.impl;

import com.sarakhman.loadanalyzer.domain.Message;
import com.sarakhman.loadanalyzer.strategy.validation.FieldValidationStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component("requestStatusValidator")
public class RequestStatusFieldValidationStrategy implements FieldValidationStrategy {
    private static final String INVALID_REQUEST_STATUS_MESSAGE = "Provided request status is invalid.";

    @Override
    public Optional<Message> validate(String field, Function<String, Message> invalidFieldMessageGenerator) {
        return isRequestStatusValid(field) ? Optional.empty() :
                Optional.of(invalidFieldMessageGenerator.apply(INVALID_REQUEST_STATUS_MESSAGE));
    }

    private boolean isRequestStatusValid(String requestStatus){
        try{
            HttpStatus.valueOf(Integer.parseInt(requestStatus));
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
