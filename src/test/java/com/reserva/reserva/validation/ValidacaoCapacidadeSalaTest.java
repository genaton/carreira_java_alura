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
public class ValidacaoCapacidadeSalaTest {

     @InjectMocks
    private ValidacaoCapacidadeSala validacaoCapacidadeSala;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando a reserva exceder o a capacidade da sala")
    void deveLancarExceptionQuandoHouverQuantidadeDeConvidadosMaiorDoQueACapacidadeDaSala() {
        // Given
        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setCapacidade(10);

        Reserva reservaMock = new Reserva(null, null, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 15, null,null);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoCapacidadeSala.validar(reservaMock, dtoMock);
        });
    }
     @Test
    @DisplayName("Não deve lançar ValidacaoException quando a reserva não exceder o a capacidade da sala")
    void naoDeveLancarExceptionQuandoHouverQuantidadeDeConvidadosMenorDoQueACapacidadeDaSala() {
        // Given
        Sala salaMock = new Sala("Sala Alpha");
        salaMock.setCapacidade(10);

        Reserva reservaMock = new Reserva(null, null, null, salaMock);
        SolicitacaoReservaDTO dtoMock = new SolicitacaoReservaDTO(1L, 1L, 10, null,null);

        // When & Then
    assertDoesNotThrow(() -> {
            validacaoCapacidadeSala.validar(reservaMock, dtoMock);
        });
    }

}
