package com.reserva.reserva.dto;


import java.time.LocalDateTime;

public record SolicitacaoReservaDTO( 
    
    Long idSala, 
    Long idUsuario, 
    Integer totalConvidados, 
    LocalDateTime dataHoraReservaInicio,
    LocalDateTime dataHoraReservaFim) {

}
