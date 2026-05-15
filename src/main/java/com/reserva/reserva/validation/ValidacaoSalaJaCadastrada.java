package com.reserva.reserva.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.repository.SalaRepository;

@Component
public class ValidacaoSalaJaCadastrada implements ValidacaoSala{

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public void validar(SolicitacaoCadastroSalaDTO dto) {
        if (salaRepository.existsByNome(dto.nome())) {
            throw new ValidacaoException("Existe uma sala com este nome");

        }
    }

}
