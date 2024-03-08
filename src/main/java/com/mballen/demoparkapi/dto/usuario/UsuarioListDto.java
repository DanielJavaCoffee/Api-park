package com.mballen.demoparkapi.dto.usuario;

import com.mballen.demoparkapi.entity.Usuario;
import java.time.LocalDateTime;

public record UsuarioListDto(
        Long id,
        String username,
        Usuario.Role role,

        LocalDateTime dataCriacao,

        LocalDateTime dataModificacao,

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
