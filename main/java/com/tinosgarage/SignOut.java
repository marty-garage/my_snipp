package com.tinosgarage;


/**
 * Simple logout page.
 *
 * @author Jonathan Locke
 */
public class SignOut extends WicketGlobalPage
{
    /**
     * Constructor
     */
    public SignOut()
    {
        logger.info("into signOUt invalidating session");
        getSession().invalidate();

    }
}