package com.mballen.demoparkapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UsuarioCreatDto(

        @Email(message = "Formato do Email inválido!", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        @NotBlank(message = "Não pode está em branco.")
        String username,

        @Size(min = 6, max = 6, message = "Tamanho min = 6 e max = 6")
        @NotBlank(message = "Não pode está em branco.")
        String password

)  {
}
