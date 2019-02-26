package com.tinosgarage;


import javax.persistence.EntityManager;

public interface DAOGeneral {


    public static EntityManager getManager(){
        MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();

        //do session exist????
        EntityManager em = app_session.getEntityManager();
        return em;
    }
}
