package com.tinosgarage;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.tinosgarage.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication
{

    /*private EntityManagerFactory emf;

    public EntityManager getEm() {
        return em;
    }

    public void initEm() {
        this.em = emf.createEntityManager();
    }

    private EntityManager em;*/


	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}



	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
	{
		return MyAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass()
	{
		return MySignInPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{

		super.init();


     //   emf = Persistence.createEntityManagerFactory("manager1");
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);

		// add your configuration here
	}
}
