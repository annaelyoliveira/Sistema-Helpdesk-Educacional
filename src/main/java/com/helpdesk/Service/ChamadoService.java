package com.helpdesk.Service;

import com.helpdesk.Exceptions.ChamadoNaoEncontradoException;
import com.helpdesk.Model.Chamado;
import com.helpdesk.Model.Tecnico;
import com.helpdesk.Repository.ChamadoRepository;
import com.helpdesk.Repository.TecnicoRepository;
import com.helpdesk.Repository.UsuarioRepository;
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

    public Chamado buscarPorId(Long id) throws ChamadoNaoEncontradoException {
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
    public void deletar(Long id) throws ChamadoNaoEncontradoException {
        chamadoRepository.deletar(id);
    }

    @Transactional
    public void atribuirTecnico(Long chamadoId, Long tecnicoId) throws ChamadoNaoEncontradoException {
        Chamado chamado = chamadoRepository.buscarPorId(chamadoId);
        Tecnico tecnico = tecnicoRepository.buscarPorId(tecnicoId);
        if (chamado != null && tecnico != null) {
            chamado.setTecnico(tecnico);
            chamado.setStatus("EM_ANDAMENTO");
            chamadoRepository.atualizar(chamado);
        }
    }
}