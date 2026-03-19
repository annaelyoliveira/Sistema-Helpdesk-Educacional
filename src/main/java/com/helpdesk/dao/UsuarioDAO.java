package com.helpdesk.dao;

import com.helpdesk.util.HibernateUtil;
import com.helpdesk.model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    public void salvar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(usuario);

        tx.commit();
        session.close();
    }
}