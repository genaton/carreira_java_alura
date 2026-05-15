package com.reserva.reserva.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.repository.SalaRepository;

@Component
public class ValidacaoSalaJaDesativada implements ValidacaoSala{
    @Autowired
    private SalaRepository salaRepository;

    public void validar(Long idSala) {

        boolean jaEstaDesativada = salaRepository.existsByIdAndAtivaFalse(idSala);

        if (jaEstaDesativada) {
            throw new ValidacaoException("Está sala já está desativada");
        }

    }

}
