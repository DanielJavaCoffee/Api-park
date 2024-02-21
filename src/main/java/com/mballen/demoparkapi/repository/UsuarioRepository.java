package com.mballen.demoparkapi.repository;

import com.mballen.demoparkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}
