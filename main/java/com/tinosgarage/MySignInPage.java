package com.tinosgarage;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;


/**
 * Simple example of a sign in page.
 *
 * @author Jonathan Locke
 */
public final class MySignInPage extends WicketGlobalPage
{
    /**
     * Constructor
     */
    public MySignInPage()
    {
        super();
        // That is all you need to add a logon panel to your application with rememberMe
        // functionality based on Cookies. Meaning username and password are persisted in a Cookie.
        // Please see ISecuritySettings#getAuthenticationStrategy() for details.
        add(new SignInPanel("signInPanel"));
        //you need to use org.apache.wicket.authroles.authentication.panel.SignInPanel
    }
}