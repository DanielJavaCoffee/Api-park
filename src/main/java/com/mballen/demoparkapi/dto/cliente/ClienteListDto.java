package com.mballen.demoparkapi.dto.cliente;

import com.mballen.demoparkapi.entity.Cliente;

import java.time.LocalDateTime;

public record ClienteListDto(
        Long id,
        String nome,
        LocalDateTime dataCriacao,
        LocalDateTime dataModificacao,
        String criadoPor,
        String modificadoPor
){
    public ClienteListDto(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDataCriacao(),
                cliente.getDataModificacao(),
                cliente.getCriadoPor(),
                cliente.getModificadoPor()
        );
    }
}

