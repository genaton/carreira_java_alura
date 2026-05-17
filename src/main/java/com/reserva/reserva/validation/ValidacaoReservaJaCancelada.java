package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;

@Component
public class ValidacaoReservaJaCancelada implements ValidacaoCancelamentoReserva {

    @Override
    public void validar(Reserva reserva) {
        if (reserva.getStatus() == EstadoReserva.CANCELADA) {
            throw new ValidacaoException("Esta reserva já se encontra cancelada");

        }

    }

}
