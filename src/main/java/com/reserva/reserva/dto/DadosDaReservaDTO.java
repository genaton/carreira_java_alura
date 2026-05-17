package com.reserva.reserva.dto;

import java.time.LocalDateTime;

import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;

public record DadosDaReservaDTO(
        Long id,
        Long idSala,
        String nomeSala,
        Long idUsuario,
        String nomeUsuario,
        LocalDateTime dataHoraReservaInicio,
        LocalDateTime dataHoraReservaFim,
        EstadoReserva status

) {

    public DadosDaReservaDTO(Reserva reserva) {
        this(
                reserva.getId(),
                reserva.getSala().getId(),
                reserva.getSala().getNome(),
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNome(),
                reserva.getDataHoraReservaInicio(),
                reserva.getDataHoraReservaFim(),
                reserva.getStatus());
    }

}
