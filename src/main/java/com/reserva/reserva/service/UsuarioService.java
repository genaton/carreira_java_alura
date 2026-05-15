package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reserva.reserva.dto.DadosDoUsuarioDTO;
import com.reserva.reserva.dto.SolicitacaoCadastroUsuarioDTO;
import com.reserva.reserva.model.Usuario;
import com.reserva.reserva.repository.UsuarioRepository;
import com.reserva.reserva.validation.ValidacaoUsuario;

@Service
public class UsuarioService {

    @Autowired
    private List<ValidacaoUsuario> validacoes;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<DadosDoUsuarioDTO> listar(Pageable paginacao) {

        return usuarioRepository.findAll(paginacao)
                .map(DadosDoUsuarioDTO::new);
    }

    public DadosDoUsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Usuário não encontrado"));
        return new DadosDoUsuarioDTO(usuario);
    }

    @Transactional
    public DadosDoUsuarioDTO  cadastrar(SolicitacaoCadastroUsuarioDTO dto) {
        
        validacoes.forEach(v -> v.validar(dto));
        Usuario novoUsuario = new Usuario(dto.nome());
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        return new DadosDoUsuarioDTO(usuarioSalvo);

    }

}
