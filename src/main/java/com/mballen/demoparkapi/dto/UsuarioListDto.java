package com.mballen.demoparkapi.dto;

import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.enuns.Role;

import java.time.LocalDate;

public record UsuarioListDto(
        Long id,
        String username,
        Role role,

        LocalDate dataCriacao,

        LocalDate dataModificacao,

        String criadoPor,

        String modificadoPor

) {
    public UsuarioListDto(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getDataCriacao(),
                usuario.getDataModificacao(),
                usuario.getCriadoPor(),
                usuario.getModificadoPor()
        );
    }
}
