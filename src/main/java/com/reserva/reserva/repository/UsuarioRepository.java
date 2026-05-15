package com.reserva.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.reserva.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByNome(String nome);

}
