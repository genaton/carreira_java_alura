package com.reserva.reserva.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.repository.ReservaRepository;

@Component
public class ValidacaoSalaLivre implements ValidacaoReserva {
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {

        if (reserva.getSala() == null) {
            throw new ValidacaoException("Não é possível validar o horário pois nenhuma sala foi associada.");
        }

        boolean jaPossuiReserva = reservaRepository
                .existsBySalaIdAndStatusAndDataHoraReservaInicioLessThanAndDataHoraReservaFimGreaterThan(
                        reserva.getSala().getId(),
                        EstadoReserva.ATIVA,
                        reserva.getDataHoraReservaFim(),
                        reserva.getDataHoraReservaInicio()

                );

        if (jaPossuiReserva) {
            throw new ValidacaoException("A sala já possui uma reserva ativa");
        }
    }

}
