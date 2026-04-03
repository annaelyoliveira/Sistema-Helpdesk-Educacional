package com.helpdesk.service;

import com.helpdesk.model.Chamado;
import com.helpdesk.model.Tecnico;
import com.helpdesk.model.Usuario;
import com.helpdesk.repository.ChamadoRepository;
import com.helpdesk.repository.TecnicoRepository;
import com.helpdesk.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChamadoService {
    
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Transactional
    public void salvar(Chamado chamado) {
        chamadoRepository.salvar(chamado);
    }

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.buscarPorId(id);
    }

    public List<Chamado> listarTodos() {
        return chamadoRepository.listarTodos();
    }

    public List<Chamado> listarAbertos() {
        return chamadoRepository.listarAbertos();
    }

    public List<Chamado> listarEmAndamento() {
        return chamadoRepository.listarEmAndamento();
    }

    public List<Chamado> listarFinalizados() {
        return chamadoRepository.listarFinalizados();
    }

    @Transactional
    public void atualizar(Chamado chamado) {
        chamadoRepository.atualizar(chamado);
    }

    @Transactional
    public void deletar(Long id) {
        chamadoRepository.deletar(id);
    }

    @Transactional
    public void atribuirTecnico(Long chamadoId, Long tecnicoId) {
        Chamado chamado = chamadoRepository.buscarPorId(chamadoId);
        Tecnico tecnico = tecnicoRepository.buscarPorId(tecnicoId);
        if (chamado != null && tecnico != null) {
            chamado.setTecnico(tecnico);
            chamado.setStatus("EM_ANDAMENTO");
            chamadoRepository.atualizar(chamado);
        }
    }
}