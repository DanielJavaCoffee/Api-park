package com.mballen.demoparkapi.controller;


import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UsuarioListDto> getByID(@PathVariable Long id) {
        return usuarioService.buscaPorID(id);
    }

    @PatchMapping()
    public ResponseEntity<UsuarioPatchSenhaDto> atualizaoDeSenha(@RequestBody @Valid UsuarioPatchSenhaDto usuario){
        return usuarioService.atualizarSenha(usuario);
    }
}
