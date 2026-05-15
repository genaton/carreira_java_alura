package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import jakarta.transaction.Transactional;

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

    public List<DadosDaReservaDTO> listar(){

        return reservaRepository.findAll()
        .stream()
        .map(DadosDaReservaDTO::new)
        .toList();
    }

    public DadosDaReservaDTO buscarPorId(Long id){
        Reserva reserva = reservaRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Reserva não encontrada"));
        return new DadosDaReservaDTO(reserva);
    }

    @Transactional
    public void agendar(SolicitacaoReservaDTO dto) {
        Sala sala = salaRepository.findById(dto.idSala()).orElseThrow(()->new IllegalArgumentException("Sala não encontrada com o ID informado"));
        
        Usuario usuario = usuarioRepository.findById(dto.idUsuario()).orElseThrow(()-> new IllegalArgumentException("Usuário não encontrado com o ID informado"));
        
        Reserva novaReserva = new Reserva(null, dto.dataHoraReservaInicio(), dto.dataHoraReservaFim(), usuario, sala);
        
        validacoes.forEach(v -> v.validar(novaReserva, dto));
        
        reservaRepository.save(novaReserva);
        
    }
    
    @Transactional
    public void cancelar(Long idReserva){
        Reserva reserva = reservaRepository.findById(idReserva).orElseThrow(()-> new IllegalArgumentException("Reserva não encontrada com o ID informado"));

        validacoesCancelamento.forEach(v -> v.validar(reserva));

       
        reserva.setStatus(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);

    }

}
