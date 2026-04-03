package com.helpdesk.Repository;

import com.helpdesk.Exceptions.UsuarioExistenteException;
import com.helpdesk.Exceptions.UsuarioNaoEncontradoException;
import com.helpdesk.Model.Usuario;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

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
        }catch (EntityExistsException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new UsuarioExistenteException("Este Usuário já Existe.");
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro inesperado ao salvar: " + e.getMessage());
        }finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String jpql = "SELECT usuario FROM Usuario usuario WHERE usuario.email = :email";
        try {
            Usuario usuario = entityManager.createQuery(jpql, Usuario.class).setParameter("email", email).getSingleResult();
            return usuario;
        } catch ( NoResultException e) {
            throw  new UsuarioNaoEncontradoException("Não foi possível encontrar o Usuário com o email " + email);
        }catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao buscar: " + e.getMessage());
        }finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
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
        }catch (UsuarioNaoEncontradoException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());

        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public void deletar(Long id) throws UsuarioNaoEncontradoException {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException("Não foi possível deletar: Usuário não encontrado.");
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (UsuarioNaoEncontradoException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}