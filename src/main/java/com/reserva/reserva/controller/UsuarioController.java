package com.reserva.reserva.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastroUsuarioDTO dto) {

        usuarioService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");

    }

    
    @GetMapping
    public ResponseEntity<List<DadosDoUsuarioDTO>> listar() {
        List<DadosDoUsuarioDTO> lista = usuarioService.listar();
        return ResponseEntity.ok(lista);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<DadosDoUsuarioDTO> buscarPorId(@PathVariable Long id) {
        DadosDoUsuarioDTO dto = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
