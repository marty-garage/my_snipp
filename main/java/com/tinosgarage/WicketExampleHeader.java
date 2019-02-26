package com.tinosgarage;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Navigation panel for the examples project.
 *
 * @author Eelco Hillenius
 */
public final class WicketExampleHeader extends Panel
{
    /**
     * Construct.
     *
     * @param id
     *            id of the component
     * @param page
     *            The example page
     */
    public WicketExampleHeader(String id)
    {
        super(id);

        setRenderBodyOnly(true);
/*
        add(new Image("exampleheaderimage", new PackageResourceReference(
                WicketGlobalPage.class, "logo-apachewicket-examples-white.svg")));*/
        add(new DebugBar("debug"));
    }
}