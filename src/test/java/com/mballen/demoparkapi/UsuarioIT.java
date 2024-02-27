package com.mballen.demoparkapi;

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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios_insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
}

// test de integração

// mvn clean install