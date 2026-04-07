package com.helpdesk.repository;

import com.helpdesk.exceptions.ChamadoNaoEncontradoException;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import com.helpdesk.model.Chamado;

import java.util.List;

@Repository
public class ChamadoRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void salvar(Chamado chamado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(chamado);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar chamado: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public Chamado buscarPorId(Long id) throws ChamadoNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Chamado chamado = entityManager.find(Chamado.class, id);
            if (chamado == null) {
                throw new ChamadoNaoEncontradoException("O Chamado com o id " + id + " não foi encontrado.");
            }
            return chamado;
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Chamado> listarTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Chamado c", Chamado.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Chamado> listarAbertos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'ABERTO'", Chamado.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Chamado> listarEmAndamento() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'EM_ANDAMENTO'", Chamado.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Chamado> listarFinalizados() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Chamado c WHERE c.status = 'FINALIZADO'", Chamado.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public void atualizar(Chamado chamado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(chamado);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar chamado: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public void deletar(Long id) throws ChamadoNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Chamado chamado = entityManager.find(Chamado.class, id);
            if (chamado == null) {
                throw new ChamadoNaoEncontradoException("O Chamado com o id " + id + " não foi encontrado.");
            }
            entityManager.remove(chamado);
            entityManager.getTransaction().commit();
        }catch (ChamadoNaoEncontradoException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar chamado: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }
}