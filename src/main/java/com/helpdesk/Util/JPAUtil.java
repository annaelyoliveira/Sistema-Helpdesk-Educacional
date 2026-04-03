package com.helpdesk.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("helpdesk-pu");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}