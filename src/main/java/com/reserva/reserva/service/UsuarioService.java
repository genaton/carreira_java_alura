package com.reserva.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reserva.reserva.dto.DadosDoUsuarioDTO;
import com.reserva.reserva.dto.SolicitacaoCadastroUsuarioDTO;
import com.reserva.reserva.model.Usuario;
import com.reserva.reserva.repository.UsuarioRepository;
import com.reserva.reserva.validation.ValidacaoUsuario;
import com.reserva.reserva.validation.ValidacaoUsuarioJaCadastrado;

@Service
public class UsuarioService {

    @Autowired
    private List<ValidacaoUsuario> validacoes;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<DadosDoUsuarioDTO> listar() {

        return usuarioRepository.findAll()
                .stream()
                .map(DadosDoUsuarioDTO::new)
                .toList();
    }

    public DadosDoUsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return new DadosDoUsuarioDTO(usuario);
    }

    @Transactional
    public void cadastrar(SolicitacaoCadastroUsuarioDTO dto) {
        Usuario novoUsuario = new Usuario(null, dto.nome());

        validacoes.forEach(v -> v.validar(dto));

        usuarioRepository.save(novoUsuario);

    }

}
