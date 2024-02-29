package com.mballen.demoparkapi;

import com.mballen.demoparkapi.dto.UsuarioListDto;
import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.exception.ErrorMensage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mballen.demoparkapi.dto.UsuarioCreatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.assertj.core.api.Assertions;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioIT.class);
    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void criarUsuario_ComUsernameEPassword_RetornaUsuarioCriadoStatus201(){

        logger.info("Iniciando teste criarUsuario_ComUsernameEPassword_RetornaUsuarioCriadoStatus201");

                UsuarioCreatDto usuarioCreatDto = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("daniel@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioCreatDto.class)
                .returnResult().getResponseBody();

                Assertions.assertThat(usuarioCreatDto).isNotNull();
                Assertions.assertThat(usuarioCreatDto.username()).isEqualTo("daniel@gmail.com");

        logger.info("Teste criarUsuario_ComUsernameEPassword_RetornaUsuarioCriadoStatus201 concluído com sucesso");
    }

    @Test
    public void criarUsuario_ComUsernameInvalido_RetornaUsuarioCriadoStatus422(){

        logger.info("Iniciando teste criarUsuario_ComUsernameInvalido_RetornaUsuarioCriadoStatus422");

        ErrorMensage errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

       Assertions.assertThat(errorMensage).isNotNull();
       Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

        errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("tody@email", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

        logger.info("Teste  criarUsuario_ComUsernameInvalido_RetornaUsuarioCriadoStatus422 concluído com sucesso");
    }

    @Test
    public void criarUsuario_ComPasswordInvalido_RetornaUsuarioCriadoStatus422(){

        logger.info("Iniciando teste criarUsuario_ComPasswordInvalido_RetornaUsuarioCriadoStatus422");

        ErrorMensage errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("today@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

        errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("tody@gmail.com", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

        errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("tody@gmail.com", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

        logger.info("Teste  criarUsuario_ComPasswordInvalido_RetornaUsuarioCriadoStatus422 concluído com sucesso");
    }

    @Test
    public void criarUsuario_ComUsernameRepetido_RetornaUsuarioCriadoStatus409(){

        logger.info("Iniciando teste criarUsuario_ComUsernameRepetido_RetornaUsuarioCriadoStatus409");

        ErrorMensage errorMensage = webTestClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreatDto("mama@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(409);

        logger.info("Teste criarUsuario_ComUsernameRepetido_RetornaUsuarioCriadoStatus409 concluído com sucesso");
    }

    @Test
    public void buscarUsuario_ComId_RetornaUsuarioCriadoStatus200(){

        logger.info("Iniciando teste buscarUsuario_ComId_RetornaUsuarioCriadoStatus200");

        UsuarioListDto usuarioListDto = webTestClient
                .get()
                .uri("/api/v1/usuarios/111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioListDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(usuarioListDto).isNotNull();
        Assertions.assertThat(usuarioListDto.id()).isEqualTo(111);
        Assertions.assertThat(usuarioListDto.username()).isEqualTo("mama@gmail.com");

        logger.info("Teste buscarUsuario_ComId_RetornaUsuarioCriadoStatus200 concluído com sucesso");
    }

    @Test
    public void buscarUsuario_ComId_InexistenteStatus404(){
        logger.info("Iniciando teste buscarUsuario_ComId_InexistenteStatus404");

        ErrorMensage errorMensage = webTestClient
                .get()
                .uri("/api/v1/usuarios/155")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMensage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMensage).isNotNull();
        Assertions.assertThat(errorMensage.getStatus()).isEqualTo(404);

        logger.info("Teste buscarUsuario_ComId_InexistenteStatus404 concluído com sucesso");
    }

    @Test
    public void testAtualizarSenha_ComUsuarioPatchSenhaDto_RetornaUsuarioPatchSenhaDtoStatus200() {
        try {
            logger.info("Iniciando teste: AtualizarSenha_ComUsuarioPatchSenhaDtoRetornaUsuarioPatchSenhaDtoStatus200");

            UsuarioPatchSenhaDto requestDto = new UsuarioPatchSenhaDto(111L, "123456", "123456", "123456");

            WebTestClient.ResponseSpec responseDto = webTestClient
                    .patch()
                    .uri("/api/v1/usuarios/atualizarSenha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestDto)
                    .exchange()
                    .expectStatus().isOk();
            Assertions.assertThat(requestDto).isNotNull();
            Assertions.assertThat(requestDto.id()).isEqualTo(111L);
            Assertions.assertThat(requestDto.senha()).isEqualTo("123456");

            logger.info("Teste concluído com sucesso: AtualizarSenha_ComUsuarioPatchSenhaDtoRetornaUsuarioPatchSenhaDtoStatus200");
        } catch (Exception e) {
            logger.error("Erro no teste: AtualizarSenha_ComUsuarioPatchSenhaDtoRetornaUsuarioPatchSenhaDtoStatus200", e);
            throw e;
        }
    }

    @Test
    public void AtualizarSenha_ComIdInexistente_Status404() {
        try {
            logger.info("Iniciando teste:  AtualizarSenha_ComIdInexistente_Status404");

            ErrorMensage errorMensage = webTestClient
                    .patch()
                    .uri("/api/v1/usuarios/atualizarSenha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UsuarioPatchSenhaDto(1L, "123456", "123456", "123456"))
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(ErrorMensage.class)
                    .returnResult().getResponseBody();
            Assertions.assertThat(errorMensage).isNotNull();
            Assertions.assertThat(errorMensage.getStatus()).isEqualTo(404);

            logger.info("Teste concluído com sucesso:  AtualizarSenha_ComIdInexistente_Status404");
        } catch (Exception e) {
            logger.error("Erro no teste:  AtualizarSenha_ComIdInexistente_Status404", e);
            throw e;
        }
    }

    @Test
    public void testAtualizarSenha_ComUsuarioPatchSenhaDtoRetornaComSenhaAtualErrada_UsuarioPatchSenhaDtoStatus400() {
        try {
            logger.info("Iniciando teste:  testAtualizarSenha_ComUsuarioPatchSenhaDtoRetornaComSenhaAtualErrada_UsuarioPatchSenhaDtoStatus400");

            ErrorMensage errorMensage = webTestClient
                    .patch()
                    .uri("/api/v1/usuarios/atualizarSenha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue( new UsuarioPatchSenhaDto(111L, "123457", "123456", "123456"))
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody(ErrorMensage.class)
                    .returnResult().getResponseBody();
            Assertions.assertThat(errorMensage).isNotNull();
            Assertions.assertThat(errorMensage.getStatus()).isEqualTo(400);

            logger.info("Teste concluído com sucesso:  testAtualizarSenha_ComUsuarioPatchSenhaDtoRetornaComSenhaAtualErrada_UsuarioPatchSenhaDtoStatus400");
        } catch (Exception e) {
            logger.error("Erro no teste:  testAtualizarSenha_ComUsuarioPatchSenhaDtoRetornaComSenhaAtualErrada_UsuarioPatchSenhaDtoStatus400", e);
            throw e;
        }
    }

    @Test
    public void testAtualizarSenha_ComUsuarioPatchDtoNovaSenhaEConfirmarSenhaErrados_RetornandoStatus400() {
        try {
            logger.info("Iniciando teste:  testAtualizarSenha_ComUsuarioPatchDtoNovaSenhaEConfirmarSenhaErrados_RetornandoStatus400");

            ErrorMensage errorMensage = webTestClient
                    .patch()
                    .uri("/api/v1/usuarios/atualizarSenha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UsuarioPatchSenhaDto(111L, "123456", "123457", "123458"))
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody(ErrorMensage.class)
                    .returnResult().getResponseBody();
            Assertions.assertThat(errorMensage).isNotNull();
            Assertions.assertThat(errorMensage.getStatus()).isEqualTo(400);

            logger.info("Teste concluído com sucesso:  testAtualizarSenha_ComUsuarioPatchDtoNovaSenhaEConfirmarSenhaErrados_RetornandoStatus400");
        } catch (Exception e) {
            logger.error("Erro no teste:  testAtualizarSenha_ComUsuarioPatchDtoNovaSenhaEConfirmarSenhaErrados_RetornandoStatus400", e);
            throw e;
        }
    }

    @Test
    public void testAtualizarSenha_ComUsuarioPatchDtoCamposInvalidos_RetornandoStatus422() {
        try {
            logger.info("Iniciando teste: testAtualizarSenha_ComUsuarioPatchDtoCamposInvalidos_RetornandoStatus422");

            ErrorMensage errorMensage = webTestClient
                    .patch()
                    .uri("/api/v1/usuarios/atualizarSenha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UsuarioPatchSenhaDto(111L, "12345", "12345", "123485434"))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody(ErrorMensage.class)
                    .returnResult().getResponseBody();
            Assertions.assertThat(errorMensage).isNotNull();
            Assertions.assertThat(errorMensage.getStatus()).isEqualTo(422);

            logger.info("Teste concluído com sucesso: testAtualizarSenha_ComUsuarioPatchDtoCamposInvalidos_RetornandoStatus422");
        } catch (Exception e) {
            logger.error("Erro no teste: testAtualizarSenha_ComUsuarioPatchDtoCamposInvalidos_RetornandoStatus422", e);
            throw e;
        }
    }

    @Test
    public void testListarUsuarios_SemQualquerParametro_RetornandoStatus200() {
        try {
            logger.info("Iniciando teste: testGetList_ComUsuarioListDto_RetornandoStatus200");

            List<UsuarioListDto> usuarioListDto = webTestClient
                    .get()
                    .uri("/api/v1/usuarios/all")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(UsuarioListDto.class)
                    .returnResult().getResponseBody();
            Assertions.assertThat(usuarioListDto).isNotNull();
            Assertions.assertThat(usuarioListDto.size()).isEqualTo(3);

            logger.info("Teste concluído com sucesso: testGetList_ComUsuarioListDto_RetornandoStatus200");
        } catch (Exception e) {
            logger.error("Erro no teste: testGetList_ComUsuarioListDto_RetornandoStatus200", e);
            throw e;
        }
    }
}

// test de integração

// mvn clean install