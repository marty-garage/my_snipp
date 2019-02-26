package com.tinosgarage;


/*import org.apache.wicket.markup.html.WebPage;

import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * A base page accessible by everybody - no authorization required.
 *
 * @author almaw

public class BasePage extends WebPage
{
    public BasePage(final PageParameters params){
        super(params);

    }



    public BasePage(){
        this(new PageParameters());
    }

    public BasePage(IModel<?> model){
        super(model);
    }
}


*/

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * A base page accessible by everybody - no authorization required.
 *
 * @author almaw
 */
public class BasePage extends WicketGlobalPage
{

    @Override
    public void renderHead(IHeaderResponse response) {


        PackageResourceReference bootstrap_js = new PackageResourceReference(this.getClass(), "/bootstrap/js/bootstrap.min.js");
        PackageResourceReference bootstrap_css = new PackageResourceReference(this.getClass(), "/bootstrap/css/bootstrap-theme.min.css");
        PackageResourceReference bootstrap = new PackageResourceReference(this.getClass(),"/bootstrap/css/bootstrap.min.css");

        CssHeaderItem item2 = CssHeaderItem.forReference(bootstrap);
        CssHeaderItem item1 = CssHeaderItem.forReference(bootstrap_css);
        JavaScriptHeaderItem item0 = JavaScriptHeaderItem.forReference(bootstrap_js);

        response.render(item0);

        response.render(item1);

        response.render(item2);
        super.renderHead(response);
    }
}