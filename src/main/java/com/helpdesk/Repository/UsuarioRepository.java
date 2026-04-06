package com.helpdesk.Repository;

import com.helpdesk.Exceptions.UsuarioExistenteException;
import com.helpdesk.Exceptions.UsuarioNaoEncontradoException;
import com.helpdesk.Model.Usuario;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void salvar(Usuario usuario) throws UsuarioExistenteException {
        try {
            entityManager.persist(usuario);
        } catch (EntityExistsException e) {
            throw new UsuarioExistenteException("Este Usuário já Existe.");
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao salvar: " + e.getMessage());
        }
    }

    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException {
        try {
            Usuario usuario = entityManager.createQuery("SELECT usuario FROM Usuario usuario WHERE usuario.email = :email", Usuario.class)
                                .setParameter("email", email)
                                .getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            throw new UsuarioNaoEncontradoException("Não foi possível encontrar o Usuário com o email " + email);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao buscar: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void atualizar(Usuario usuario) throws UsuarioNaoEncontradoException {
        try {
            if (usuario.getId() == null || entityManager.find(Usuario.class, usuario.getId()) == null) {
                throw new UsuarioNaoEncontradoException("Não é possível atualizar: Usuário inexistente.");
            }
            entityManager.merge(usuario);
        } catch (UsuarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public Usuario deletar(Long id) throws UsuarioNaoEncontradoException {
        try {
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario == null) {
                throw new UsuarioNaoEncontradoException("Não foi possível deletar: Usuário não encontrado.");
            }
            entityManager.remove(usuario);
            return usuario;
        } catch (UsuarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}