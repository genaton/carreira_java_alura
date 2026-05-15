package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<DadosDaSalaDTO> listar() {

        return salaRepository.findAll()
                .stream()
                .map(DadosDaSalaDTO::new)
                .toList();
    }

    public DadosDaSalaDTO buscarPorId(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada"));
        return new DadosDaSalaDTO(sala);
    }

    @Transactional
    public void cadastrar(SolicitacaoCadastroSalaDTO dto) {
        Sala novaSala = new Sala(dto.nome(), dto.capacidade());

        validacoes.forEach(v -> v.validar(dto));

        salaRepository.save(novaSala);

    }

    @Transactional
    public void desativar(Long idSala) {
        validacoes.forEach(v -> v.validar(idSala));

        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com o ID informado"));


        sala.setAtiva(false);
        salaRepository.save(sala);

    }

}
