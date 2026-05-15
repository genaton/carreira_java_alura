package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;

@Component
public class ValidacaoCapacidadeSala implements ValidacaoReserva {

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {
        if (dto.totalConvidados() <= 0) {
            throw new ValidacaoException("Informe um número positivo");
        }

        if (dto.totalConvidados() > reserva.getSala().getCapacidade()) {
            throw new ValidacaoException("A quantidade de convidados excede a capacidade máxima permitida pela sala.");
        }
    }
}
