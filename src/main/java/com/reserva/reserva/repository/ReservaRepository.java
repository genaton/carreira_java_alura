package com.reserva.reserva.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsBySalaIdAndStatusAndDataHoraReservaInicioLessThanAndDataHoraReservaFimGreaterThan(
            Long salaId,
            EstadoReserva status,
            LocalDateTime inicio,
            LocalDateTime fim);

    // JPQL Otimizada: Traz a Reserva, a Sala e o Usuário em UMA ÚNICA query (Evita
    // N+1 nas listagens paginadas)
    @Query(value = """
                SELECT r FROM Reserva r
                JOIN FETCH r.sala
                JOIN FETCH r.usuario
            """, countQuery = "SELECT COUNT(r) FROM Reserva r")
    Page<Reserva> findAllOtimizado(Pageable paginacao);

    // Busca por ID customizada para também carregar os relacionamentos LAZY em uma
    // única pancada
    @Query("SELECT r FROM Reserva r JOIN FETCH r.sala JOIN FETCH r.usuario WHERE r.id = :id")
    Optional<Reserva> findByIdOtimizado(@Param("id") Long id);

}
