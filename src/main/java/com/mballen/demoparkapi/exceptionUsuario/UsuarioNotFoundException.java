package com.mballen.demoparkapi.exceptionUsuario;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException() {
        super("Usuário não encontrado!");
    }
}
