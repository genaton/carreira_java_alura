package com.reserva.reserva.validation;

import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;

@Component
public class ValidacaoCapacidadeSala implements ValidacaoReserva {

    @Override
    public void validar(Reserva reserva, SolicitacaoReservaDTO dto) {
        if (dto.totalConvidados() == null || dto.totalConvidados() <= 0) {
            throw new ValidacaoException("Informe um número positivo");
        }
        
        Sala sala = reserva.getSala();
        
        if(sala == null){
            
            throw new ValidacaoException("Não é possível validar a capacidade pois nenhuma sala foi associada à reserva.");
        }

        if (dto.totalConvidados() > sala.getCapacidade()) {
            throw new ValidacaoException("\"A quantidade de convidados excede a capacidade máxima permitida pela sala (\" \n" + //
                                "                + sala.getCapacidade() + \" pessoas).\"");
        }
    }
}
