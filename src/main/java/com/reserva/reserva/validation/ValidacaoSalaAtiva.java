package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;

@Component
public class ValidacaoSalaAtiva implements ValidacaoReserva {

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {
        Sala sala = reserva.getSala();

        if (sala == null) {
            throw new ValidacaoException(
                    "Não é possível validar o status pois nenhuma sala foi vinculada a esta reserva.");
        }

        if (!sala.getAtiva()) {
            throw new ValidacaoException("Sala inativa. Por favor, escolha outra");
        }
    }

}
