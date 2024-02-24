package com.mballen.demoparkapi.exceptionUsuario;

public class UsernameUniqueViolationException extends RuntimeException{

    public UsernameUniqueViolationException(String mensage){
        super(mensage);
    }
}
