package com.helpdesk.service;

import com.helpdesk.exceptions.*;
import com.helpdesk.model.Usuario;
import com.helpdesk.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void salvar(Usuario usuario) throws UsuarioExistenteException {
        usuarioRepository.salvar(usuario);
    }

    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException {
        return usuarioRepository.buscarPorEmail(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    @Transactional
    public void atualizar(Usuario usuario) throws UsuarioNaoEncontradoException {
        usuarioRepository.atualizar(usuario);
    }

    @Transactional
    public void deletar(Long id) throws UsuarioNaoEncontradoException {
        usuarioRepository.deletar(id);
    }
}
