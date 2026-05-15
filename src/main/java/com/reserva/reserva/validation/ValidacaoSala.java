package com.reserva.reserva.validation;

import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;

public interface ValidacaoSala {
    default void validar(SolicitacaoCadastroSalaDTO dto){}
    default void validar(Long idSala){}

}
