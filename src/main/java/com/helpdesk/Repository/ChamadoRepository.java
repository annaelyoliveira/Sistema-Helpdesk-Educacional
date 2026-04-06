package com.helpdesk.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.helpdesk.Model.Chamado;

import java.util.List;

@Repository
public class ChamadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void salvar(Chamado chamado) {
        entityManager.persist(chamado);
    }

    public Chamado buscarPorId(Long id) {
        return entityManager.find(Chamado.class, id);
    }

    public List<Chamado> listarTodos() {
        return entityManager.createQuery("SELECT c FROM Chamado c", Chamado.class).getResultList();
    }

    public List<Chamado> listarAbertos() {
        return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'ABERTO'", Chamado.class).getResultList();
    }

    public List<Chamado> listarEmAndamento() {
        return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'EM_ANDAMENTO'", Chamado.class).getResultList();
    }

    public List<Chamado> listarFinalizados() {
        return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'FINALIZADO'", Chamado.class).getResultList();
    }

    public void atualizar(Chamado chamado) {
        entityManager.merge(chamado);
    }

    public void deletar(Long id) {
        Chamado chamado = entityManager.find(Chamado.class, id);
        if (chamado != null) {
            entityManager.remove(chamado);
        }
    }
}