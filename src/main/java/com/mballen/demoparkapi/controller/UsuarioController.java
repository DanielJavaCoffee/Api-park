package com.mballen.demoparkapi.controller;

import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.exceptionUsuario.UsuarioNotFoundException;
import com.mballen.demoparkapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioCreatDto> create(@RequestBody @Valid UsuarioCreatDto usuarioCreateDto){
        return usuarioService.creat(usuarioCreateDto);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioListDto>> listaDeUsuarios(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable Long id) {
        try {
           UsuarioListDto usuarioListDto = usuarioService.buscaPorId(id);
           return ResponseEntity.ok(usuarioListDto);
        } catch (UsuarioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/atualizarSenha")
    public ResponseEntity<String> atualizarSenha(@RequestBody @Valid UsuarioPatchSenhaDto usuarioPatchSenhaDto) {
        try {
            boolean senhaAtualizada = usuarioService.atualizarSenha(usuarioPatchSenhaDto);

            if (senhaAtualizada) {
                return ResponseEntity.ok("Senha atualizada com sucesso");
            } else {
                return ResponseEntity.badRequest().body("Falha ao atualizar a senha");
            }
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
