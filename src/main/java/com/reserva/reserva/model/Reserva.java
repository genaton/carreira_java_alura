package com.reserva.reserva.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_hora_reserva_inicio", nullable = false)
    private LocalDateTime dataHoraReservaInicio;
    @Column(name = "data_hora_reserva_fim", nullable = false)
    private LocalDateTime dataHoraReservaFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva status = EstadoReserva.ATIVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    public Reserva(LocalDateTime dataHoraReservaInicio, LocalDateTime dataHoraReservaFim, Usuario usuario,
            Sala sala) {

        this.dataHoraReservaInicio = dataHoraReservaInicio;
        this.dataHoraReservaFim = dataHoraReservaFim;
        this.usuario = usuario;
        this.sala = sala;
    }

   

}
