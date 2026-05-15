package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;

@Component
public class ValidacaoSalaAtiva implements ValidacaoReserva{

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {
        if(!reserva.getSala().isAtiva()){
            throw new ValidacaoException("Sala inativa. Por favor, escolha outra");
        }
    }



}
