package com.mballen.demoparkapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioPatchSenhaDto(
        @NotNull
        Long id,

        @NotBlank
        @Size(min = 6, max = 6, message = "Tamanho min = 6 e max = 6")
        String senha,

        @NotBlank
        @Size(min = 6, max = 6, message = "Tamanho min = 6 e max = 6")
        String novaSenha,

        @NotBlank
        @Size(min = 6, max = 6, message = "Tamanho min = 6 e max = 6")
        String confirmarSenha
) {
}
