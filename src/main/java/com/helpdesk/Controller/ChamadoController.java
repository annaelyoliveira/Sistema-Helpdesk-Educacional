package com.helpdesk.Controller;

import com.helpdesk.Exceptions.ChamadoNaoEncontradoException;
import com.helpdesk.Model.Chamado;
import com.helpdesk.Service.ChamadoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@CrossOrigin(origins = "*")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Chamado chamado) {
        chamadoService.salvar(chamado);
        return ResponseEntity.ok("Chamado criado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscarPorId(@PathVariable Long id) throws ChamadoNaoEncontradoException {
        Chamado chamado = chamadoService.buscarPorId(id);
        if (chamado != null) {
            return ResponseEntity.ok(chamado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Chamado>> listarTodos() {
        List<Chamado> chamados = chamadoService.listarTodos();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/abertos")
    public ResponseEntity<List<Chamado>> listarAbertos() {
        List<Chamado> chamados = chamadoService.listarAbertos();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/em-andamento")
        public ResponseEntity<List<Chamado>> listarEmAndamento() {
            List<Chamado> chamados = chamadoService.listarEmAndamento();
            return ResponseEntity.ok(chamados);
    }

    @GetMapping("/finalizados")
        public ResponseEntity<List<Chamado>> listarFinalizados() {
            List<Chamado> chamados = chamadoService.listarFinalizados();
            return ResponseEntity.ok(chamados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Chamado chamado) {
        chamado.setId(id);
        chamadoService.atualizar(chamado);
        return ResponseEntity.ok("Chamado atualizado");
    }

    @PutMapping("/{id}/atribuir/{tecnicoId}")
    public ResponseEntity<String> atribuirTecnico(@PathVariable Long id, @PathVariable Long tecnicoId) throws ChamadoNaoEncontradoException {
        chamadoService.atribuirTecnico(id, tecnicoId);
        return ResponseEntity.ok("Técnico atribuído");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) throws ChamadoNaoEncontradoException {
        chamadoService.deletar(id);
        return ResponseEntity.ok("Chamado deletado");
    }
}