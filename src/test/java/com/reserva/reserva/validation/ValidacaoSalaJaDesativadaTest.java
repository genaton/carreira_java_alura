package com.reserva.reserva.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;
import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;
import com.reserva.reserva.repository.ReservaRepository;
import com.reserva.reserva.repository.SalaRepository;

@ExtendWith(MockitoExtension.class)

public class ValidacaoSalaJaDesativadaTest {

    @InjectMocks
    private ValidacaoSalaJaDesativada validacaoSalaJaDesativada;

    @Mock
    private SalaRepository salaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando desativar sala já desativada")
    void deveLancarExceptionQuandoASalaJaEstiverDesativada() {
        // Given
        Long salaId = 1L;

        when(salaRepository.existsByIdAndAtivaFalse(salaId)).thenReturn(true);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoSalaJaDesativada.validar(salaId);
        });

    }

    @Test
    @DisplayName("Não deve lançar ValidacaoException quando desativar sala")
    void naoDeveLancarExceptionQuandoADesativarSala() {
        // Given
        Long salaId = 1L;

        when(salaRepository.existsByIdAndAtivaFalse(salaId)).thenReturn(false);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoSalaJaDesativada.validar(salaId);
        });

    }

}
