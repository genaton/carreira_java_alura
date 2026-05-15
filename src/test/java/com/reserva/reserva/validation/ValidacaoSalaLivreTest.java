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

import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;
import com.reserva.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
class ValidacaoSalaLivreTest {

    @InjectMocks
    private ValidacaoSalaLivre validacaoSalaLivre;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando a sala já possuir uma reserva ativa no período")
    void deveLancarExceptionQuandoHouverConflitoDeHorario() {
        // Given
        Long salaId = 1L;
        LocalDateTime inicio = LocalDateTime.of(2026, 5, 15, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 5, 15, 16, 0);

        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setId(salaId);

        Reserva reservaMock = new Reserva(inicio, fim, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(salaId, 1L, 10, inicio, fim);

        // Simula que o banco ENCONTROU um conflito (retorna true)
        when(reservaRepository.existsBySalaIdAndStatusAndDataHoraReservaInicioLessThanAndDataHoraReservaFimGreaterThan(
                salaId, EstadoReserva.ATIVA, fim, inicio)).thenReturn(true);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoSalaLivre.validar(reservaMock, dtoMock);
        });
    }

    @Test
    @DisplayName("Não deve lançar exceção quando a sala estiver livre no período selecionado")
    void naoDeveLancarExceptionQuandoHorarioEstiverLivre() {
        // Given
        Long salaId = 1L;
        LocalDateTime inicio = LocalDateTime.of(2026, 5, 15, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 5, 15, 16, 0);

        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setId(salaId);

        Reserva reservaMock = new Reserva(inicio, fim, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(salaId, 1L, 10, inicio, fim);

        // Simula que o banco NÃO ENCONTROU conflitos (retorna false)
        when(reservaRepository.existsBySalaIdAndStatusAndDataHoraReservaInicioLessThanAndDataHoraReservaFimGreaterThan(
                salaId, EstadoReserva.ATIVA, fim, inicio)).thenReturn(false);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoSalaLivre.validar(reservaMock, dtoMock);
        });
    }

    @Test
    @DisplayName("Deve lançar ValidacaoException quando o objeto Sala dentro da Reserva for nulo")
    void deveLancarExceptionQuandoSalaForNula() {
        // Given
        LocalDateTime inicio = LocalDateTime.of(2026, 5, 15, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 5, 15, 16, 0);

        // Criamos uma reserva informando NULL no parâmetro da Sala para forçar o erro defensivo
        Reserva reservaMock = new Reserva(inicio, fim, null, null);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 10, inicio, fim);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoSalaLivre.validar(reservaMock, dtoMock);
        });
    }
}
