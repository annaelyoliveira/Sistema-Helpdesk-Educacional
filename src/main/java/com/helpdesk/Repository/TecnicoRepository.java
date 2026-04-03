package com.helpdesk.Repository;

import com.helpdesk.Model.Tecnico;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class TecnicoRepository {

    public  void salvar(Tecnico tecnico) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(tecnico);
        tx.commit();
        session.close();
    }

    public Tecnico  buscar(String email) {

    }
}
