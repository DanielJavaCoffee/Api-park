package com.mballen.demoparkapi.exceptionUsuario;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String mensage){
        super(mensage);
    }
}
