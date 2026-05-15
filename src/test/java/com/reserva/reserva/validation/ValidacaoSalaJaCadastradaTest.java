package com.reserva.reserva.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.repository.SalaRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoSalaJaCadastradaTest {

    @InjectMocks
    private ValidacaoSalaJaCadastrada validacaoSalaJaCadastrada;
    @Mock
    private SalaRepository salaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando a sala já cadastrada com o mesmo nome")
    void deveLancarExceptionQuandoHouverSalaJaCadastradaComMesmoNome() {
        // Given
        String nomeExistente = "Sala Alpha";

        SolicitacaoCadastroSalaDTO dtoMock = new SolicitacaoCadastroSalaDTO(nomeExistente, 15);

        // Simula que o banco ENCONTROU um conflito (retorna true)
        when(salaRepository.existsByNome(nomeExistente)).thenReturn(true);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoSalaJaCadastrada.validar(dtoMock);
        });
    }
    @Test
    @DisplayName("Não deve lançar ValidacaoException quando cadastrar nova sala")
    void naoDeveLancarExceptionQuandoCadastrarSalaNova() {
        // Given
        String novoNome = "Sala Alpha";

        SolicitacaoCadastroSalaDTO dtoMock = new SolicitacaoCadastroSalaDTO(novoNome, 15);

        // Simula que o banco ENCONTROU um conflito (retorna true)
        when(salaRepository.existsByNome(novoNome)).thenReturn(false);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoSalaJaCadastrada.validar(dtoMock);
        });
    }

}
