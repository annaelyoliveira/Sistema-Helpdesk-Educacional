package com.helpdesk.controller;

import com.helpdesk.model.Tecnico;
import com.helpdesk.service.TecnicoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
@CrossOrigin(origins = "*")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Tecnico tecnico) {
        tecnicoService.salvar(tecnico);
        return ResponseEntity.ok("Técnico criado com sucesso");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Tecnico> buscarPorEmail(@PathVariable String email) {
        Tecnico tecnico = tecnicoService.buscarPorEmail(email);
        if (tecnico != null) {
            return ResponseEntity.ok(tecnico);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> listarTodos() {
        List<Tecnico> tecnicos = tecnicoService.listarTodos();
        return ResponseEntity.ok(tecnicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Tecnico tecnico) {
        tecnico.setId(id);
        tecnicoService.atualizar(tecnico);
        return ResponseEntity.ok("Técnico atualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.ok("Técnico deletado");
    }
}