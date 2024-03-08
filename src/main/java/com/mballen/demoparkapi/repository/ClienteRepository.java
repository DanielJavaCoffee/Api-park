package com.mballen.demoparkapi.repository;

import com.mballen.demoparkapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
