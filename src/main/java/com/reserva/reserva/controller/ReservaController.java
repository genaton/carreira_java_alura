package com.reserva.reserva.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.reserva.dto.DadosDaReservaDTO;
import com.reserva.reserva.dto.SolicitacaoReservaDTO;
import com.reserva.reserva.service.ReservaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<String> agendar(@RequestBody @Valid SolicitacaoReservaDTO dto) {

        reservaService.agendar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Reserva agendada com sucesso");

    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {

        reservaService.cancelar(id);

        return ResponseEntity.ok("Reserva cancelada com sucesso");

    }

    @GetMapping
    public ResponseEntity<List<DadosDaReservaDTO>> listar() {
        List<DadosDaReservaDTO> lista = reservaService.listar();
        return ResponseEntity.ok(lista);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<DadosDaReservaDTO> buscarPorId(@PathVariable Long id) {
        DadosDaReservaDTO dto = reservaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }
    

}
