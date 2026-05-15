package com.reserva.reserva.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsBySalaIdAndDataHoraReservaInicioLessThanAndDataHoraReservaFimGreaterThanAndStatus(
        Long salaId,
        LocalDateTime fim,
        LocalDateTime inicio,
        EstadoReserva status
    );

}
