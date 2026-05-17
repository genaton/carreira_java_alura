package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reserva.reserva.dto.DadosDaSalaDTO;
import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;
import com.reserva.reserva.model.Sala;
import com.reserva.reserva.repository.SalaRepository;
import com.reserva.reserva.validation.ValidacaoSala;

@Service
public class SalaService {

    @Autowired
    private List<ValidacaoSala> validacoes;

    @Autowired
    private SalaRepository salaRepository;

    public Page<DadosDaSalaDTO> listar(Pageable paginacao) {

        return salaRepository.findAll(paginacao)
                .map(DadosDaSalaDTO::new);
    }

    public DadosDaSalaDTO buscarPorId(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Sala não encontrada"));
        return new DadosDaSalaDTO(sala);
    }

    @Transactional
    public DadosDaSalaDTO cadastrar(SolicitacaoCadastroSalaDTO dto) {
        validacoes.forEach(v -> v.validar(dto));

        Sala novaSala = new Sala(dto.nome());
        novaSala.setCapacidade(dto.capacidade());

        Sala salaSalva = salaRepository.save(novaSala);
        return new DadosDaSalaDTO(salaSalva);

    }

    @Transactional
    public void desativar(Long idSala) {
        validacoes.forEach(v -> v.validar(idSala));

        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Sala não encontrada com o ID informado"));

        sala.setAtiva(false);
        salaRepository.save(sala);

    }

}
