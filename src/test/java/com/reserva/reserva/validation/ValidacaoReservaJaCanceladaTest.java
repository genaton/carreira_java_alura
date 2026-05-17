package com.reserva.reserva.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoReservaJaCanceladaTest {

    @InjectMocks
    private ValidacaoReservaJaCancelada validacaoReservaJaCancelada;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando cancela uma reserva já cancelada")
    void deveLancarExceptionQuandoHouverCancelamentoDeReservaJaCancelada() {
        // Given
        Reserva reservaMock = new Reserva();
        reservaMock.setStatus(EstadoReserva.CANCELADA);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoReservaJaCancelada.validar(reservaMock);
        });
    }

    @Test
    @DisplayName("Não deve lançar ValidacaoException quando cancela uma reserva")
    void naoDeveLancarExceptionQuandoHouverCancelamentoDeReserva() {
        // Given
        Reserva reservaMock = new Reserva();
        reservaMock.setStatus(EstadoReserva.ATIVA);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoReservaJaCancelada.validar(reservaMock);
        });
    }
}
