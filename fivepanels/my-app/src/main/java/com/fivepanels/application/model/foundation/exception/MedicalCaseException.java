package com.fivepanels.application.model.foundation.exception;

public class MedicalCaseException extends RuntimeException {

    public MedicalCaseException(String message) {

        super(message);
    }

    public MedicalCaseException(String message, Throwable cause) {

        super(message, cause);
    }
}
