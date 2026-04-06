package com.helpdesk.Controller;

import com.helpdesk.Exceptions.UsuarioExistenteException;
import com.helpdesk.Exceptions.UsuarioNaoEncontradoException;
import com.helpdesk.Model.Usuario;
import com.helpdesk.Service.UsuarioService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Usuario usuario) {
        try {
            usuarioService.salvar(usuario);
            return ResponseEntity.ok("Usuário criado com sucesso");
        } catch (UsuarioExistenteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(usuario);
        }catch (UsuarioNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            usuarioService.atualizar(usuario);
            return ResponseEntity.ok("Usuário atualizado");
        } catch (UsuarioNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.deletar(id);
            return ResponseEntity.ok("Usuário com email:" + usuario.getEmail()+ " deletado.");
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
