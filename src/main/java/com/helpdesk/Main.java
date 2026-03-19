package com.helpdesk;

import com.helpdesk.dao.UsuarioDAO;
import com.helpdesk.model.Usuario;

public class Main {

    public static void main(String[] args) {

        Usuario usuario = new Usuario();
        usuario.setNome("Anna");
        usuario.setEmail("anna@email.com");

        UsuarioDAO dao = new UsuarioDAO();
        dao.salvar(usuario);

        System.out.println("Usuário salvo com sucesso!");
    }
}