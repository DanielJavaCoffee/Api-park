package com.mballen.demoparkapi.controller;

import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.entity.Usuario;
import com.mballen.demoparkapi.exception.ErrorMensage;
import com.mballen.demoparkapi.exceptionUsuario.UsuarioNotFoundException;
import com.mballen.demoparkapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Usuarios.", description = "Contem todas as operaçoes relativas ao recursos para cadastro, edição e leitura de um usuário.")

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo Usuário.", description = "Recurso para criar um novo Usuário.",
               responses = {
               @ApiResponse(responseCode = "201",
                            description = "Rescurso criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreatDto.class))),

               @ApiResponse(responseCode = "409",
                            description = "Usuário já cadastrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMensage.class))),

              @ApiResponse(responseCode = "422",
                           description = "Dados de entradas inválidos.",
                           content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMensage.class))),
              }
    )
    @PostMapping
    public ResponseEntity<UsuarioCreatDto> create(@RequestBody @Valid UsuarioCreatDto usuarioCreateDto){
        UsuarioCreatDto createdUsuario = usuarioService.create(usuarioCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @Operation( summary = "Listar todos os usuarios.", description = "Recurso para listar todos os usuarios.",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recurso solicitado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioListDto.class))
            )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioListDto>> listaDeUsuarios(){
        return usuarioService.findAll();
    }

    @Operation( summary = "Busca de usuário por id.", description = "Recurso para buscar usuário por id.",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioListDto.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema (implementation = ErrorMensage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListDto> getByID(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscaPorId(id));
    }

    @Operation(summary = "Atualizar senha.", description = "Recurso para atualização de senha.",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Senha Atualizada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioPatchSenhaDto.class))),

            @ApiResponse(
                    responseCode = "400",
                    description = "A senha atual fornecida não é a mesma senha já cadastrada!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMensage.class))),

            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema (implementation = ErrorMensage.class))),

            @ApiResponse(
                    responseCode = "422",
                    description = "Dados de entradas inválidos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMensage.class)))
           }
    )
    @PatchMapping("/atualizarSenha")
    public ResponseEntity<String> atualizarSenha(@RequestBody @Valid UsuarioPatchSenhaDto usuarioPatchSenhaDto) {
        boolean senhaAtualizada = usuarioService.atualizarSenha(usuarioPatchSenhaDto);
        if (senhaAtualizada) {
            return ResponseEntity.ok("Senha atualizada com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Falha ao atualizar a senha");
        }
    }
}
