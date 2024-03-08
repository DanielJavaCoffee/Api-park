package com.mballen.demoparkapi.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
public record ClienteCreateDto(

        @NotBlank
        @Size(min = 5, max = 100)
        String nome,

        @NotBlank
        @Size(min = 11, max = 11)
        @CPF
        String cpf
) {
}
