package com.reserva.reserva.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reserva.reserva.dto.SolicitacaoCadastroUsuarioDTO;
import com.reserva.reserva.exception.ValidacaoException;
import com.reserva.reserva.repository.UsuarioRepository;

@Component

public class ValidacaoUsuarioJaCadastrado implements ValidacaoUsuario {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(SolicitacaoCadastroUsuarioDTO dto) {
        if (usuarioRepository.existsByNome(dto.nome())) {
            throw new ValidacaoException("Existe um usuário com este nome");

        }

    }
}
