package com.mballen.demoparkapi.entity;

import com.mballen.demoparkapi.dto.UsuarioPatchSenhaDto;
import com.mballen.demoparkapi.enuns.Role;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Normalized;

import java.io.Serializable;
import java.time.LocalDate;
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @Column(name = "username", nullable = false, unique = true, length = 150)
    private String username;

    @Column(name = "password", nullable = false, length = 300)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;
    @Column(name = "criado_por")
    private String criadoPor;
    @Column(name = "modificado_por")
    private String modificadoPor;

    public void atualizarSenha(@Valid UsuarioPatchSenhaDto usuarioPatchSenhaDto){
        if(usuarioPatchSenhaDto.novaSenha() != null){
            this.password = usuarioPatchSenhaDto.novaSenha();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;

        return getId().equals(usuario.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Usuario {" + "id=" + id + '}';
    }
}
