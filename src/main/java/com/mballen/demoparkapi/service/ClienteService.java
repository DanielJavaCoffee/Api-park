package com.mballen.demoparkapi.service;

import com.mballen.demoparkapi.dto.cliente.ClienteCreateDto;
import com.mballen.demoparkapi.entity.Cliente;
import com.mballen.demoparkapi.exceptionUsuario.CpfUniqueViolationException;
import com.mballen.demoparkapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteCreateDto salvar(ClienteCreateDto clienteCreateDto){
        try {
            var cliente = new Cliente();
            BeanUtils.copyProperties(clienteCreateDto, cliente);
            clienteRepository.save(cliente);
            return clienteCreateDto;
        } catch (DataIntegrityViolationException exception){
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado. Já existe esse CPF no sistema. ", clienteCreateDto.cpf()));
        }
    }

}
