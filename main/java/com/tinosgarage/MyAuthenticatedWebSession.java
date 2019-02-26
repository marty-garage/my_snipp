package com.tinosgarage;


import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Authenticated session subclass. Note that it is derived from AuthenticatedWebSession which is
 * defined in the auth-role module.
 *
 * @author Jonathan Locke
 */
public class MyAuthenticatedWebSession extends AuthenticatedWebSession
{
    final static java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();

    private EntityManagerFactory emf; //= Persistence.createEntityManagerFactory("manager1");
    private   EntityManager em;

    private Users user = null;

    public Users getCurrentUser(){
        return this.user;
    }
    /**
     * Construct.
     *
     * @param request
     *            The current request object
     */
    public MyAuthenticatedWebSession(Request request)
    {
        super(request);
        //com.h
       // org.hibernate.jpa.HibernatePersistenceProvider.class
        logger.info("into MyAuthenticatedEwbSession");
        // Use persistence.xml configuration
        try {
            emf = Persistence.createEntityManagerFactory("manager1");
        }catch (Error e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
         em = emf.createEntityManager(); // Retrieve an application managed entity manager
        // Work with the EM

    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        logger.info("autenthicating wicket user");

        Users current_user = new Users(username,password);
        try {
                current_user.setId((Long) em.createQuery("SELECT id FROM Users WHERE mail=:name AND pwd=:pass")
                    .setParameter("pass", password)
                    .setParameter("name", username).getSingleResult()); //TODO: ecrypt user info on db table
        }catch (NoResultException ex){
            return false;
        }
        this.setAttribute("user",current_user.getId());
        this.user = current_user;
        // Check username and password

        //--------- get session ad manager ------
        MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();
        logger.info("web session is: "+app_session.toString());
        EntityManager em = app_session.getEntityManager();
        //---------------------------------------------------
        Object one_row_result = null;
        try {
            Query q = em.createNativeQuery("SELECT COLUMN_TYPE FROM information_schema.COLUMNS" +
                    "        WHERE TABLE_SCHEMA = 'snippets'" +
                    "        AND TABLE_NAME = 'SnipMeta'" +
                    "        AND COLUMN_NAME  = 'tags'");


            one_row_result = q.getSingleResult();
            //em.flush();
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        String type = null;

        logger.info(type = ""+one_row_result);


        String patternString1 = "'([a-zA-Z]+)'";  //escape???

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(type);
        ArrayList<String> tmp_list = new ArrayList<String>();

        while(matcher.find()) {
            tmp_list.add(matcher.group(1));

            //listItems.add(new Label(listItems.newChildId(), matcher.group(1)));
        }
        this.setAttribute("tags_set",tmp_list);


        return current_user.getMail().equals(username) && current_user.getPwd().equals(password);
    }

    @Override
    public Roles getRoles()
    {
        if (isSignedIn())
        {
            //RequestCycle.get().getRequest()

            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }

    @Override
    public void invalidate(){
        em.close();
        //...
        emf.close(); //close at application end
        super.invalidate();
    }

    public EntityManager getEntityManager(){

        return this.em;
    }


    public static MyAuthenticatedWebSession getYourAppSession() {
        return (MyAuthenticatedWebSession) Session.get();
    }
}