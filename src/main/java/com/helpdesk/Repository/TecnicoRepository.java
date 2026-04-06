package com.helpdesk.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.helpdesk.Model.Tecnico;

import java.util.List;

@Repository
public class TecnicoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void salvar(Tecnico tecnico) {
        entityManager.persist(tecnico);
    }

    public Tecnico buscarPorEmail(String email) {
        try {
            return entityManager.createQuery("SELECT t FROM Tecnico t WHERE t.email = :email", Tecnico.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Tecnico buscarPorId(Long id) {
        return entityManager.find(Tecnico.class, id);
    }

    public List<Tecnico> listarTodos() {
        return entityManager.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();
    }

    public void atualizar(Tecnico tecnico) {
        entityManager.merge(tecnico);
    }

    public void deletar(Long id) {
        Tecnico tecnico = entityManager.find(Tecnico.class, id);
        if (tecnico != null) {
            entityManager.remove(tecnico);
        }
    }
}
