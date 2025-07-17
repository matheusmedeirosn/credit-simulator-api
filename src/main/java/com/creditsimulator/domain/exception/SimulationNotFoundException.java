package com.creditsimulator.domain.exception;

import java.io.IOException;

public class SimulationNotFoundException extends IOException {

    private static final long serialVersionUID = 1L;

    public SimulationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimulationNotFoundException(String message) {
        super(message);
    }

}
