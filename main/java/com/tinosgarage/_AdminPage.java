package com.tinosgarage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;


/**
 * A page only accessible by a user in the ADMIN role.
 *
 * @author Jonathan Locke
 */
@AuthorizeInstantiation("ADMIN")
public class _AdminPage extends BasePage
{

}
