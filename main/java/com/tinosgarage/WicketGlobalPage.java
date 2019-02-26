package com.tinosgarage;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Base class for all example pages.
 *
 * @author Jonathan Locke
 */
public class WicketGlobalPage extends WebPage
{
    private static final long serialVersionUID = 1L;

    static final java.util.logging.Logger logger = java.util.logging.Logger.getAnonymousLogger();

    /**
     * Constructor
     */
    public WicketGlobalPage()
    {
        this(new PageParameters());
    }

    /**
     * Constructor
     *
     * @param pageParameters
     */
    public WicketGlobalPage(final PageParameters pageParameters)
    {
        super(pageParameters);
/*
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("sources",
                SourcesPage.class, SourcesPage.generatePageParameters(this));
        add(link);

        link.setVisible(showSourceButton());

        PopupSettings settings = new PopupSettings("sources", PopupSettings.RESIZABLE);
        settings.setWidth(800);
        settings.setHeight(600);
        link.setPopupSettings(settings);
        */
        add(buildHeader("pageHeader"));

        explain();
    }

    protected boolean showSourceButton()
    {
        return true;
    }

    protected Panel buildHeader(String id)
    {
        return new WicketExampleHeader(id);
    }


    /**
     * Construct.
     *
     * @param model
     */
    public WicketGlobalPage(IModel<?> model)
    {
        super(model);
    }

    /**
     * Override base method to provide an explanation
     */
    protected void explain()
    {
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        response.render(CssHeaderItem.forReference(new CssResourceReference(WicketGlobalPage.class, "style.css"),"screen"));
    }
}