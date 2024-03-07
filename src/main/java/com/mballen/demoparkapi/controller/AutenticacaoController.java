package com.mballen.demoparkapi.controller;

import com.mballen.demoparkapi.dto.UsuarioLoginDto;
import com.mballen.demoparkapi.exception.ErrorMessage;
import com.mballen.demoparkapi.jwt.JwtToken;
import com.mballen.demoparkapi.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticaçao.", description = "Recurso para proceder com autenticação na API.")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class AutenticacaoController {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final AuthenticationManager authenticationManager;

    @Operation( summary = "Fazer autenticação.", description = "Recurso para fazer  autenticação na API.",
            responses = {
            @ApiResponse(responseCode = "200", description = "Autenticação feita com sucesso e retorno de uma Bearer token.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioLoginDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Credencias inválidas.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ), @ApiResponse( responseCode = "422", description = "Campo(s) inválido(s).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto, HttpServletRequest request){
        log.info("Processo de autenticação por login {} " + usuarioLoginDto.username());
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDto.username(), usuarioLoginDto.password());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            JwtToken jwtToken = jwtUserDetailsService.getTokenAuthenticated(usuarioLoginDto.username());
            return ResponseEntity.ok(jwtToken);
        }catch (AuthenticationException exception){
            log.warn("Bad credentials from username{} " + usuarioLoginDto.username());
        }
        return ResponseEntity.badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credencias inválidas"));
    }
}
