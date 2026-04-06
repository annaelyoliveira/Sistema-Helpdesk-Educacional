package com.helpdesk.Repository;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import com.helpdesk.Model.Tecnico;
import java.util.List;

@Repository
public class TecnicoRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void salvar(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tecnico);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar técnico: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public Tecnico buscarPorEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Tecnico t WHERE t.email = :email", Tecnico.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public Tecnico buscarPorId(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Tecnico.class, id);
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Tecnico> listarTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public void atualizar(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar técnico: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public void deletar(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tecnico tecnico = entityManager.find(Tecnico.class, id);
            if (tecnico != null) {
                entityManager.remove(tecnico);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar técnico: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }
}