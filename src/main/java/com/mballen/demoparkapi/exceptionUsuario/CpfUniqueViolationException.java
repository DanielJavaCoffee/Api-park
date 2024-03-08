package com.mballen.demoparkapi.exceptionUsuario;

public class CpfUniqueViolationException extends RuntimeException {

    public CpfUniqueViolationException(String message){
        super(message);
    }
}
