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
    public ResponseEntity<DadosDaSalaDTO> cadastrar(@RequestBody @Valid SolicitacaoCadastroSalaDTO dto, UriComponentsBuilder uriBuilder) {

      DadosDaSalaDTO salaCriada =  salaService.cadastrar(dto);
      URI uri = uriBuilder.path("/api/v1/salas/{id}").buildAndExpand(salaCriada.idSala()).toUri();

        return ResponseEntity.created(uri).body(salaCriada);

    }

      @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        salaService.desativar(id);
        return ResponseEntity.noContent().build(); // Retorna 24 No Content (padrão para exclusão/desativação lógica)
    }

    @GetMapping
    public ResponseEntity<Page<DadosDaSalaDTO>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        Page<DadosDaSalaDTO> pagina = salaService.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<DadosDaSalaDTO> buscarPorId(@PathVariable Long id) {
        DadosDaSalaDTO dto = salaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
