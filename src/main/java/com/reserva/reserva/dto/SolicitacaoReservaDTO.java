package com.reserva.reserva.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SolicitacaoReservaDTO(

        @NotNull(message = "O ID da sala é obrigatório.") 
        Long idSala,

        @NotNull(message = "O ID do usuário é obrigatório.") 
        Long idUsuario,

        @Positive(message = "O total de convidados deve ser um número maior que zero.") 
        Integer totalConvidados,

        @NotNull(message = "A data e hora de início da reserva é obrigatória.") 
        @Future(message = "A data de início da reserva deve ser uma data futura.") 
        LocalDateTime dataHoraReservaInicio,
        
        @NotNull(message = "A data e hora de início da reserva é obrigatória.") 
        @Future(message = "A data de início da reserva deve ser uma data futura.") 
        LocalDateTime dataHoraReservaFim) {

}
