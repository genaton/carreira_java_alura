package com.reserva.reserva.model;


import java.time.LocalDateTime;
import java.util.Objects;

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

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraReservaInicio;
    private LocalDateTime dataHoraReservaFim;

    @Enumerated(EnumType.STRING)
    private EstadoReserva status = EstadoReserva.ATIVA;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false) 
    private Sala sala;

    public Reserva() {
    }

    public Reserva(Long id, LocalDateTime dataHoraReservaInicio, LocalDateTime dataHoraReservaFim, Usuario usuario,
            Sala sala) {
        this.id = id;
        this.dataHoraReservaInicio = dataHoraReservaInicio;
        this.dataHoraReservaFim = dataHoraReservaFim;
        this.usuario = usuario;
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraReservaInicio() {
        return dataHoraReservaInicio;
    }

    public void setDataHoraReservaInicio(LocalDateTime dataHoraReservaInicio) {
        this.dataHoraReservaInicio = dataHoraReservaInicio;
    }

    public LocalDateTime getDataHoraReservaFim() {
        return dataHoraReservaFim;
    }

    public void setDataHoraReservaFim(LocalDateTime dataHoraReservaFim) {
        this.dataHoraReservaFim = dataHoraReservaFim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public EstadoReserva getStatus() {
        return status;
    }

    public void setStatus(EstadoReserva status) {
        this.status = status;
    }

}
