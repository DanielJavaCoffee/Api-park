package com.mballen.demoparkapi.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioPatchSenhaDto(
        @NotNull
        Long id,

        @NotNull
        String password
) {
}
