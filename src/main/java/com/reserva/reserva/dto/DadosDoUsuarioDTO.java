package com.reserva.reserva.dto;

import com.reserva.reserva.model.Usuario;

public record DadosDoUsuarioDTO(

    Long idUsuario,
    String nome
    )
 {

    public DadosDoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome());
    }
 
}
