package com.mballen.demoparkapi;

import com.mballen.demoparkapi.dto.usuario.UsuarioLoginDto;
import com.mballen.demoparkapi.exception.ErrorMessage;
import com.mballen.demoparkapi.jwt.JwtToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_login_status200(){
        JwtToken jwtToken = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mama@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(jwtToken).isNotNull();
    }

    @Test
    public void test_loginUsernameESenhaErrado_status400(){

         ErrorMessage errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mamar@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(400);

        errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mama@gmail.com", "123455"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(400);
    }
    @Test
    public void test_loginComCrendenciasUsernameErrado_status422(){

        ErrorMessage errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
    }

    @Test
    public void test_loginComCrendenciasPasswordErrado_status422(){

        ErrorMessage errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mama@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mama@gmail.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        errorMessage = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("mama@gmail.com", "1231232"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
    }
}
