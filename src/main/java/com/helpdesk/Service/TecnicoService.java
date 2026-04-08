package com.helpdesk.Service;

import com.helpdesk.Model.Tecnico;
import com.helpdesk.Repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TecnicoService {
    
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void salvar(Tecnico tecnico) {
        tecnico.setSenha(passwordEncoder.encode(tecnico.getSenha()));
        tecnico.setRole("ROLE_TECNICO");
        tecnicoRepository.salvar(tecnico);
    }

    public Tecnico buscarPorEmail(String email) {
        return tecnicoRepository.buscarPorEmail(email);
    }

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.listarTodos();
    }

    @Transactional
    public void atualizar(Tecnico tecnico) {
        tecnicoRepository.atualizar(tecnico);
    }

    @Transactional
    public void deletar(Long id) {
        tecnicoRepository.deletar(id);
    }
}