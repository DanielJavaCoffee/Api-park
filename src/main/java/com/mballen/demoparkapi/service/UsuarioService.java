package com.mballen.demoparkapi.service;

import ch.qos.logback.core.net.server.Client;
import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Transactional
    public ResponseEntity<UsuarioCreatDto> creat(UsuarioCreatDto usuarioCreateDto){
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCreateDto, usuario);
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreateDto);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioListDto>> findAll() {
        List<UsuarioListDto> users = usuarioRepository.findAll().stream().map(UsuarioListDto::new).toList();
        return ResponseEntity.ok(users);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioListDto> buscaPorID(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            var usuario = usuarioOptional.get();
            UsuarioListDto usuarioListDto = new UsuarioListDto(usuario);
            return ResponseEntity.ok(usuarioListDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @Transactional
    public ResponseEntity<Void> atualizarSenha(UsuarioPatchSenhaDto usuarioPatchSenhaDto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioPatchSenhaDto.id());

        if (usuarioOptional.isPresent()){
            var usuario = usuarioOptional.get();
            if(!usuario.getPassword().equals(usuarioPatchSenhaDto.senha())){
                throw  new RuntimeException("A senha atual fornecida não é a mesma senha já cadastrada!");
            }
            if(!usuarioPatchSenhaDto.novaSenha().equals(usuarioPatchSenhaDto.confirmarSenha())){
                throw new RuntimeException("As senhas não são iguais!");
            }
            usuario.atualizarSenha(usuarioPatchSenhaDto);
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
