package com.mballen.demoparkapi.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioCreatDto(

        @NotNull
        String username,

        @NotNull
        String password

)  {
}
