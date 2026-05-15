package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;

@Component
public class ValidacaoPeriodoReserva implements ValidacaoReserva {

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {
        if (reserva.getDataHoraReservaInicio()
                .isAfter(reserva.getDataHoraReservaFim()) ||
                reserva.getDataHoraReservaInicio()
                        .isEqual(reserva.getDataHoraReservaFim())) {
            throw new ValidacaoException("A data/hora de inicio da reserva deve ser anterior à data de término");

        }
    }

}
