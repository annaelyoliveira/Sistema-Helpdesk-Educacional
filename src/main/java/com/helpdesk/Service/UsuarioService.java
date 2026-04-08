package com.helpdesk.Service;

import com.helpdesk.Exceptions.UsuarioExistenteException;
import com.helpdesk.Exceptions.UsuarioNaoEncontradoException;
import com.helpdesk.Model.Usuario;
import com.helpdesk.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void salvar(Usuario usuario) throws UsuarioExistenteException {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole("ROLE_USUARIO");
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
    public Usuario deletar(Long id) throws UsuarioNaoEncontradoException {
        return usuarioRepository.deletar(id);
    }
}
