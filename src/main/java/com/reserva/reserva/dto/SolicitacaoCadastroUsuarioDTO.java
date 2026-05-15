package com.reserva.reserva.dto;

import jakarta.validation.constraints.NotBlank;

public record SolicitacaoCadastroUsuarioDTO(
    @NotBlank(message = "nome do usuário é obrigatório")
    String nome    
   
) {

}
