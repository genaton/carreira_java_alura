package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class ReservaService {

    @Autowired
    private List<ValidacaoReserva> validacoes;

    @Autowired
    private List<ValidacaoCancelamentoReserva> validacoesCancelamento;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public Page<DadosDaReservaDTO> listar(Pageable paginacao) {

        return reservaRepository.findAllOtimizado(paginacao)
                .map(DadosDaReservaDTO::new);
    }

    @Transactional(readOnly = true)
    public DadosDaReservaDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findByIdOtimizado(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        return new DadosDaReservaDTO(reserva);
    }

    @Transactional
    public DadosDaReservaDTO agendar(SolicitacaoReservaDTO dto) {
        Sala sala = salaRepository.findById(dto.idSala())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada com o ID informado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID informado"));

        Reserva novaReserva = new Reserva(dto.dataHoraReservaInicio(), dto.dataHoraReservaFim(), usuario, sala);
        validacoes.forEach(v -> v.validar(novaReserva, dto));
        Reserva reservaSalva = reservaRepository.save(novaReserva);
        return new DadosDaReservaDTO(reservaSalva);

    }

    @Transactional
    public void cancelar(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com o ID informado"));

        validacoesCancelamento.forEach(v -> v.validar(reserva));

        reserva.setStatus(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);

    }

}
