package com.reserva.reserva.validation;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoPeriodoReservaTest {

     @InjectMocks
    private ValidacaoPeriodoReserva validacaoPeriodoReserva;

    
    @Test
    @DisplayName("Deve lançar ValidacaoException quando reservar com data/hora final for anterior à data/hora inicial")
    void deveLancarExceptionQuandoHouverDataHoraFinalMenorQueDataHoraInicial() {
        // Given
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 10, 14, 0);
        LocalDateTime fimAnterior = LocalDateTime.of(2026, 7, 10, 13, 0);
        Reserva reservaMock = new Reserva(inicio, fimAnterior, null, null);
         SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 5, inicio, fimAnterior);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoPeriodoReserva.validar(reservaMock, dtoMock);
        });
    }

    @Test
    @DisplayName("Deve lançar ValidacaoException quando reservar com data/hora inicial for igual  à data/hora final")
    void deveLancarExceptionQuandoHouverDataHoraInicialIgualADataHoraFinal() {
        // Given
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 10, 14, 0);
        LocalDateTime fimAnterior = LocalDateTime.of(2026, 7, 10, 14, 0);
        Reserva reservaMock = new Reserva(inicio, fimAnterior, null, null);
         SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 5, inicio, fimAnterior);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoPeriodoReserva.validar(reservaMock, dtoMock);
        });
    }

    @Test
    @DisplayName("Não deve lançar ValidacaoException quando reservar com data/hora inicial menor que  data/hora final")
    void naoDeveLancarExceptionQuandoHouverDataHoraInicialMenorQueADataHoraFinal() {
        // Given
        LocalDateTime inicio = LocalDateTime.of(2026, 7, 10, 13, 0);
        LocalDateTime fimAnterior = LocalDateTime.of(2026, 7, 10, 14, 0);
        Reserva reservaMock = new Reserva(inicio, fimAnterior, null, null);
         SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 5, inicio, fimAnterior);

        // When & Then
        assertDoesNotThrow( () -> {
            validacaoPeriodoReserva.validar(reservaMock, dtoMock);
        });
    }

}
