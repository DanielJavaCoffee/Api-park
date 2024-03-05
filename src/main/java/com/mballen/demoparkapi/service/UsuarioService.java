package com.mballen.demoparkapi.service;

import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.exceptionUsuario.PasswordInvalidException;
import com.mballen.demoparkapi.exceptionUsuario.UsernameUniqueViolationException;
import com.mballen.demoparkapi.exceptionUsuario.UsuarioNotFoundException;
import com.mballen.demoparkapi.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UsuarioCreatDto create(UsuarioCreatDto usuarioCreateDto) {
        try {
            var usuario = new Usuario();
            BeanUtils.copyProperties(usuarioCreateDto, usuario);
            usuario.setPassword(passwordEncoder.encode(usuarioCreateDto.password()));
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
            if (!passwordEncoder.matches(usuarioPatchSenhaDto.senha(), usuario.getPassword())) {
                throw new PasswordInvalidException("A senha atual fornecida não é a mesma senha já cadastrada!");
            }
            if (!usuarioPatchSenhaDto.novaSenha().equals(usuarioPatchSenhaDto.confirmarSenha())) {
                throw new PasswordInvalidException("As senhas não são iguais!");
            }
            usuario.setPassword(passwordEncoder.encode(usuarioPatchSenhaDto.novaSenha()));
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
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
