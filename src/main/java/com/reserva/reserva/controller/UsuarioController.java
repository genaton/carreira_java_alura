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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.reserva.reserva.dto.DadosDoUsuarioDTO;
import com.reserva.reserva.dto.SolicitacaoCadastroUsuarioDTO;
import com.reserva.reserva.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DadosDoUsuarioDTO> cadastrar(@RequestBody @Valid SolicitacaoCadastroUsuarioDTO dto, UriComponentsBuilder uriBilder) {

        DadosDoUsuarioDTO usuarioCriado = usuarioService.cadastrar(dto);

        URI uri = uriBilder.path("/api/v1/usuarios/{id}").buildAndExpand(usuarioCriado.idUsuario()).toUri();

        return ResponseEntity.created(uri).body(usuarioCriado);

    }

    
    @GetMapping
    public ResponseEntity<Page<DadosDoUsuarioDTO>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        Page<DadosDoUsuarioDTO> pagina = usuarioService.listar(paginacao);
        return ResponseEntity.ok(pagina);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<DadosDoUsuarioDTO> buscarPorId(@PathVariable Long id) {
        DadosDoUsuarioDTO dto = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
