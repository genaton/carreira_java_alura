package com.reserva.reserva.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<DadosDaReservaDTO> agendar(@RequestBody @Valid SolicitacaoReservaDTO dto,
            UriComponentsBuilder uriBuilder) {

        DadosDaReservaDTO reservaCriada = reservaService.agendar(dto);

        URI uri = uriBuilder.path("/api/v1/reservas/{id}").buildAndExpand(reservaCriada.id()).toUri();

        return ResponseEntity.created(uri).body(reservaCriada);

    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {

        reservaService.cancelar(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<DadosDaReservaDTO>> listar(@PageableDefault(size = 10, sort = "dataHoraReservaInicio") Pageable paginacao) {
        Page<DadosDaReservaDTO> pagina = reservaService.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDaReservaDTO> buscarPorId(@PathVariable Long id) {
        DadosDaReservaDTO dto = reservaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
