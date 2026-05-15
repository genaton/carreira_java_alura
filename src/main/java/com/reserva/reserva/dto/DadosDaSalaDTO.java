package com.reserva.reserva.dto;

import com.reserva.reserva.model.Sala;

public record DadosDaSalaDTO(

    Long idSala, 
    String nome, 
    Integer capacidade, 
    Boolean ativa)
 {
 public DadosDaSalaDTO(Sala sala) {
        this(sala.getId(), sala.getNome(), sala.getCapacidade(), sala.isAtiva());
    }
}
