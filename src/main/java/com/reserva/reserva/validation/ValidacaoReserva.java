package com.reserva.reserva.validation;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.model.Reserva;

public interface ValidacaoReserva {

    void validar(Reserva reserva, SolicitacaoReservaDTO dto);

}
