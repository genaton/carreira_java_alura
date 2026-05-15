package com.reserva.reserva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SolicitacaoCadastroSalaDTO(
    @NotBlank(message = "nome da sala é obrigatório")
    String nome,
    
    @Positive(message = "A capaciade da sala deve ser um número positivo")
    Integer capacidade
) {

}
