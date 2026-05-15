package com.reserva.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.reserva.model.Sala;


public interface SalaRepository extends JpaRepository<Sala, Long>{

    boolean existsByNome(String nome);

    boolean existsByIdAndAtivaFalse(Long idSala);

    

    

}
