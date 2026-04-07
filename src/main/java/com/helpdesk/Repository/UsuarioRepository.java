package com.helpdesk.repository;

import com.helpdesk.exceptions.UsuarioExistenteException;
import com.helpdesk.exceptions.UsuarioNaoEncontradoException;
import com.helpdesk.model.Usuario;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public void salvar(Usuario usuario) throws UsuarioExistenteException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } catch (EntityExistsException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new UsuarioExistenteException("Este Usuário já Existe.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro inesperado ao salvar: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UsuarioNaoEncontradoException("Não foi possível encontrar o Usuário com o email " + email);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao buscar: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public List<Usuario> listarTodos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public void atualizar(Usuario usuario) throws UsuarioNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (usuario.getId() == null || entityManager.find(Usuario.class, usuario.getId()) == null) {
                throw new UsuarioNaoEncontradoException("Não é possível atualizar: Usuário inexistente.");
            }
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } catch (UsuarioNaoEncontradoException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }

    public Usuario deletar(Long id) throws UsuarioNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException("Não foi possível deletar: Usuário não encontrado.");
            }
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
            return usuario;
        } catch (UsuarioNaoEncontradoException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }
}