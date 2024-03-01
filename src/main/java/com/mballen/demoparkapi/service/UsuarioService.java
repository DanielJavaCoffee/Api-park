package com.mballen.demoparkapi.service;

import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.exceptionUsuario.PasswordInvalidException;
import com.mballen.demoparkapi.exceptionUsuario.UsernameUniqueViolationException;
import com.mballen.demoparkapi.exceptionUsuario.UsuarioNotFoundException;
import com.mballen.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
    public UsuarioCreatDto create(UsuarioCreatDto usuarioCreateDto) {
        try {
            var usuario = new Usuario();
            BeanUtils.copyProperties(usuarioCreateDto, usuario);
            usuarioRepository.save(usuario);
            return usuarioCreateDto;
        } catch (DataIntegrityViolationException exception) {
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuarioCreateDto.username()));
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioListDto>> findAll() {
        List<UsuarioListDto> users = usuarioRepository.findAll().stream().map(UsuarioListDto::new).toList();
        return ResponseEntity.ok(users);
    }

    @Transactional(readOnly = true)
    public UsuarioListDto buscaPorId(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if(usuarioOptional.isPresent()){
            var usuario = usuarioOptional.get();
            UsuarioListDto usuarioListDto =  new UsuarioListDto(usuario);
            return usuarioListDto;
        }
        throw new UsuarioNotFoundException();
    }

    @Transactional
    public boolean atualizarSenha(UsuarioPatchSenhaDto usuarioPatchSenhaDto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioPatchSenhaDto.id());

        if (usuarioOptional.isPresent()) {
            var usuario = usuarioOptional.get();
            if (!usuario.getPassword().equals(usuarioPatchSenhaDto.senha())) {
                throw new PasswordInvalidException("A senha atual fornecida não é a mesma senha já cadastrada!");
            }
            if (!usuarioPatchSenhaDto.novaSenha().equals(usuarioPatchSenhaDto.confirmarSenha())) {
                throw new PasswordInvalidException("As senhas não são iguais!");
            }
            usuario.atualizarSenha(usuarioPatchSenhaDto);
            usuarioRepository.save(usuario);
            return true;
        }
        throw new UsuarioNotFoundException();
    }

    @Transactional
    public void excluirUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException());
        usuarioRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscaPorUsername(String username) {
        return usuarioRepository.findByName(username).orElseThrow(
                () -> new UsuarioNotFoundException()
        );
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscaRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
