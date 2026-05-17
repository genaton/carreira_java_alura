package com.reserva.reserva.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.reserva.dto.DadosDaReservaDTO;
import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.model.EstadoReserva;
import com.reserva.reserva.model.Reserva;
import com.reserva.reserva.model.Sala;
import com.reserva.reserva.model.Usuario;
import com.reserva.reserva.repository.ReservaRepository;
import com.reserva.reserva.repository.SalaRepository;
import com.reserva.reserva.repository.UsuarioRepository;
import com.reserva.reserva.validation.ValidacaoCancelamentoReserva;
import com.reserva.reserva.validation.ValidacaoReserva;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Spy
    private List<ValidacaoReserva> validacoes = new ArrayList<>(); 
    
    @Spy
    private List<ValidacaoCancelamentoReserva> validacoesCancelamento = new ArrayList<>();

    @Test
    @DisplayName("Deve agendar uma reserva com sucesso quando os dados informados forem válidos")

    
    void deveAgendarReservaComSucessoQuandoDadosForemValidos() {
        // Given (Cenário com dados claros e controlados)
        Long salaId = 1L;
        Long usuarioId = 2L;
        LocalDateTime inicio = LocalDateTime.of(2026, 6, 20, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 6, 20, 12, 0);

        SolicitacaoReservaDTO dto = new SolicitacaoReservaDTO(salaId, usuarioId, 8, inicio, fim);

        Sala salaMock = new Sala("Sala Executiva");
        salaMock.setId(salaId);

        Usuario usuarioMock = new Usuario("João Silva");
        usuarioMock.setId(usuarioId);

        Reserva reservaSalvaMock = new Reserva(100L, inicio, fim, EstadoReserva.ATIVA, usuarioMock, salaMock);

        // Configura o comportamento dos mocks do repositório
        when(salaRepository.findById(salaId)).thenReturn(Optional.of(salaMock));
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaSalvaMock);

        // When (Executa a ação de agendamento)
        DadosDaReservaDTO resposta = reservaService.agendar(dto);

        // Then (Garante a atomicidade e integridade do retorno do DTO)
        assertNotNull(resposta);
        assertEquals(100L, resposta.id());
        assertEquals("Sala Executiva", resposta.nomeSala());
        assertEquals("João Silva", resposta.nomeUsuario());
        assertEquals(EstadoReserva.ATIVA, resposta.status());

        // Valida se o banco de dados foi acionado para salvar exatamente uma única vez
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar agendar reserva com uma sala inexistente")
    void deveLancarEntityNotFoundExceptionQuandoSalaNaoExistir() {
        // Given (Tentando agendar para uma sala com ID 999 que não existe no banco)
        SolicitacaoReservaDTO dto = new SolicitacaoReservaDTO(999L, 1L, 5, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2));

        // Força o repositório de salas a retornar um Optional vazio
        when(salaRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then (O serviço deve interromper o fluxo e lançar o erro correto para
        // o Handler)
        assertThrows(EntityNotFoundException.class, () -> {
            reservaService.agendar(dto);
        });

        // Garante que o processo foi interrompido antes de tentar salvar a reserva
        // inválida
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

        @Test
    @DisplayName("Deve interromper o agendamento e lançar ValidacaoException quando algum validador falhar")
    void deveLancarExceptionQuandoAlgumValidadorFalhar() {
        // Given
        Long salaId = 1L;
        Long usuarioId = 2L;
        LocalDateTime inicio = LocalDateTime.of(2026, 6, 20, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 6, 20, 12, 0);
        SolicitacaoReservaDTO dto = new SolicitacaoReservaDTO(salaId, usuarioId, 8, inicio, fim);

        Sala salaMock = new Sala("Sala Executiva");
        Usuario usuarioMock = new Usuario("João Silva");

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(salaMock));
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));

        // Criamos um mock específico para um validador e forçamos ele a lançar um erro
        ValidacaoReserva validadorQueFalhaMock = mock(ValidacaoReserva.class);
        doThrow(new com.reserva.reserva.exception.ValidacaoException("Erro simulado de validador"))
            .when(validadorQueFalhaMock).validar(any(Reserva.class), any(SolicitacaoReservaDTO.class));

        // Adicionamos o validador mockado na nossa lista Spy do teste
        validacoes.add(validadorQueFalhaMock);

        // When & Then
        assertThrows(com.reserva.reserva.exception.ValidacaoException.class, () -> {
            reservaService.agendar(dto);
        });

        
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

        @Test
    @DisplayName("Deve cancelar uma reserva com sucesso quando ela for encontrada e passar pelas validações")
    void deveCancelarReservaComSucessoQuandoDadosForemValidos() {
        // Given (Cenário: Reserva ativa cadastrada no banco)
        Long idReserva = 1L;
        Reserva reservaMock = new Reserva();
        reservaMock.setId(idReserva);
        reservaMock.setStatus(EstadoReserva.ATIVA);

        when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reservaMock));
        when(reservaRepository.save(reservaMock)).thenReturn(reservaMock);

        // When (Executa a ação de cancelamento)
        assertDoesNotThrow(() -> reservaService.cancelar(idReserva));

        // Then (Valida se o status mudou e o banco foi atualizado de forma atômica)
        assertEquals(EstadoReserva.CANCELADA, reservaMock.getStatus());
        verify(reservaRepository, times(1)).save(reservaMock);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar cancelar uma reserva inexistente")
    void deveLancarExceptionAoCancelarReservaInexistente() {
        // Given (Tentando cancelar ID 999 que não existe)
        Long idInexistente = 999L;
        when(reservaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // When & Then (O fluxo deve ser interrompido imediatamente)
        assertThrows(EntityNotFoundException.class, () -> reservaService.cancelar(idInexistente));

        // Garante que nenhuma alteração de status ou salvamento foi disparado
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

        @Test
    @DisplayName("Deve retornar DadosDaReservaDTO quando buscar por um ID existente")
    void deveRetornarDtoAoBuscarPorIdExistente() {
        // Given
        Long idReserva = 1L;
        Sala salaMock = new Sala("Sala A");
        Usuario usuarioMock = new Usuario("Marta");
        Reserva reservaMock = new Reserva(idReserva, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), EstadoReserva.ATIVA, usuarioMock, salaMock);

        // Nota: O service utiliza o método otimizado findByIdOtimizado
        when(reservaRepository.findByIdOtimizado(idReserva)).thenReturn(Optional.of(reservaMock));

        // When
        DadosDaReservaDTO resultado = reservaService.buscarPorId(idReserva);

        // Then
        assertNotNull(resultado);
        assertEquals(idReserva, resultado.id());
        assertEquals("Sala A", resultado.nomeSala());
    }

      @Test
    @DisplayName("Deve retornar uma página de DTOs ao listar as reservas")
    void deveRetornarPageDeDtosAoListar() {
        // Given (Mockando a estrutura de paginação do Spring Data usando PageImpl)
        org.springframework.data.domain.Pageable paginacao = org.springframework.data.domain.PageRequest.of(0, 10);
        
        // CORREÇÃO: Usando PageImpl para instanciar de forma pública e estável uma página vazia
        org.springframework.data.domain.Page<Reserva> paginaMock = new org.springframework.data.domain.PageImpl<>(java.util.Collections.emptyList(), paginacao, 0);

        // Nota: O service utiliza o método otimizado findAllOtimizado
        when(reservaRepository.findAllOtimizado(paginacao)).thenReturn(paginaMock);

        // When
        org.springframework.data.domain.Page<DadosDaReservaDTO> resultado = reservaService.listar(paginacao);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty()); // Certifica que a estrutura mapeou corretamente a lista vazia
        verify(reservaRepository, times(1)).findAllOtimizado(paginacao);
    }




}
