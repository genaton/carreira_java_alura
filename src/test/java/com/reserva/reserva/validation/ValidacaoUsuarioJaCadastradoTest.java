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

import com.reserva.reserva.dto.SolicitacaoCadastroUsuarioDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)

public class ValidacaoUsuarioJaCadastradoTest {

     @InjectMocks
    private ValidacaoUsuarioJaCadastrado validacaoUsuarioJaCadastrado;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve lançar ValidacaoException quando usuário já cadastrado com o mesmo nome")
    void deveLancarExceptionQuandoHouverUsuarioJaCadastradaComMesmoNome() {
        // Given
        String nomeExistente = "José Gomes";

        SolicitacaoCadastroUsuarioDTO dtoMock = new SolicitacaoCadastroUsuarioDTO(nomeExistente);

        // Simula que o banco ENCONTROU um conflito (retorna true)
        when(usuarioRepository.existsByNome(nomeExistente)).thenReturn(true);

        // When & Then
        assertThrows(ValidacaoException.class, () -> {
            validacaoUsuarioJaCadastrado.validar(dtoMock);
        });
    }
    @Test
    @DisplayName("Não deve lançar ValidacaoException quando cadastrar novo usuario")
    void naoDeveLancarExceptionQuandoCadastrarNovoUsuario() {
        // Given
        String novoNome = "José Gomes";

         SolicitacaoCadastroUsuarioDTO dtoMock = new SolicitacaoCadastroUsuarioDTO(novoNome);

        // Simula que o banco ENCONTROU um conflito (retorna true)
        when(usuarioRepository.existsByNome(novoNome)).thenReturn(false);

        // When & Then
        assertDoesNotThrow(() -> {
            validacaoUsuarioJaCadastrado.validar(dtoMock);
        });
    }

}
