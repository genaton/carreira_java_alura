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

import com.reserva.reserva.dto.DadosDaSalaDTO;
import com.reserva.reserva.dto.SolicitacaoCadastroSalaDTO;
import com.reserva.reserva.service.SalaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastroSalaDTO dto) {

        salaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Sala criada com sucesso");

    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<String> desativar(@PathVariable Long id) {

        salaService.desativar(id);

        return ResponseEntity.ok("Sala desativada com sucesso");

    }

    @GetMapping
    public ResponseEntity<List<DadosDaSalaDTO>> listar() {
        List<DadosDaSalaDTO> lista = salaService.listar();
        return ResponseEntity.ok(lista);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<DadosDaSalaDTO> buscarPorId(@PathVariable Long id) {
        DadosDaSalaDTO dto = salaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
