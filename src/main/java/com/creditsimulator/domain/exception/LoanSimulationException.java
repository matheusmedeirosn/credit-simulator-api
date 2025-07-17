package com.creditsimulator.domain.exception;

public class LoanSimulationException extends Exception {
    public LoanSimulationException(String message) {
        super(message);
    }

    public LoanSimulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
