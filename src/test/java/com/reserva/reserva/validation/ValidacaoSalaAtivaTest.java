package com.reserva.reserva.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;
import com.reserva.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoSalaAtivaTest {

    @InjectMocks
    private ValidacaoSalaAtiva validacaoSalaAtiva;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando reservar uma sala não ativa")
    void deveLancarExceptionQuandoReservarSalaNaoAtiva() {

        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setAtiva(false);

        Reserva reservaMock = new Reserva(null, null, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 10, null, null);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoSalaAtiva.validar(reservaMock, dtoMock);
        });
    }

    @Test
    @DisplayName("Não deve lançar ValidacaoException quando reservar uma sala ativa")
    void naoDeveLancarExceptionQuandoReservarSalaAtiva() {

        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setAtiva(true);

        Reserva reservaMock = new Reserva(null, null, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 10, null, null);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoSalaAtiva.validar(reservaMock, dtoMock);
        });
    }
}
