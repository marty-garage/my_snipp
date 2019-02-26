package com.tinosgarage;


import org.apache.commons.lang.StringUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static javax.persistence.FlushModeType.COMMIT;

@AuthorizeInstantiation("ADMIN")
public class SnippetBook extends BasePage
{
    /** A global list of all snip */
    private  List<Snip> snipList = new ArrayList<>();
    //private EntityManager em = DAOGeneral.getManager();
    /**
     * Constructor that is invoked when page is invoked without a session.
     */
    public SnippetBook()
    {

        MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();
        logger.info("web session is: "+app_session.toString());

        EntityManager em = app_session.getEntityManager();


       List<Snip> tmp_snip_list= em.createQuery(
                "SELECT s FROM Snip s WHERE users.id = :userID")
                .setParameter("userID", app_session.getAttribute("user"))
                .getResultList(); //TODO: set max result number

       //logger.info(((Snip)tmp_snip_list.get(0)).getCode());
        /*AuthenticatedWebApplication.get().
        Class<? extends Session> session_class = getSession();.getClass();
        getSession().getApplication().getSessionStore();
        */
        // Add comment form

        add(new SnippetForm("commentForm"));

        // Add commentListView of existing comments
        add(new PropertyListView<Snip>("comments", snipList)
        {
            @Override
            public void populateItem(final ListItem<Snip> listItem)
            {
                //logger.info(listItem.toString());
                listItem.add(new Label("date"));
                listItem.add(new MultiLineLabel("code"));
            }
        }).setVersioned(false);
        Collections.reverse(tmp_snip_list);
        snipList.addAll(tmp_snip_list);
    }



    /**
     * A form that allows a user to add a comment.
     *
     * @author Jonathan Locke
     */
    public  class SnippetForm extends Form<ValueMap>
    {
        /**
         * Constructor
         *
         * @param id
         *            The name of this component
         */
        public SnippetForm(final String id)
        {
            // Construct form with no validation listener
            super(id, new CompoundPropertyModel<>(new ValueMap()));




            // this is just to make the unit test happy
            setMarkupId("commentForm");

            // Add text entry widget
            add(new TextArea<>("code").setType(String.class));
            add(new TextArea<>("meta").setType(String.class));


            ArrayList<String> tag_list = (ArrayList<String>)MyAuthenticatedWebSession.getYourAppSession().getAttribute("tags_set");

            //ListModel<String> selected_tags;
            ListMultipleChoice listItems = new ListMultipleChoice("select_all",tag_list);


            add(listItems);

            // Add simple automated spam prevention measure.
            add(new TextField<>("comment").setType(String.class));


        }

        /**
         * Show the resulting valid edit
         */
        @Override
        @Transactional
        public  void onSubmit() {
            ValueMap values = getModelObject();

            // check if the honey pot is filled
            if (StringUtils.isNotBlank((String)values.get("comment")))
            {
                error("Caught a spammer!!!");
                return;
            }
            // Construct a copy of the edited comment
            Snip snip = new Snip();
            SnipMeta meta= new SnipMeta();

            logger.info(this.getDefaultModel().toString());

            logger.info(((List<String>) values.get("select_all")).toString());

            meta.setTags((ArrayList<String>)values.get("select_all"));
            meta.setDescription((String) values.get("meta"));


            // Set date of comment to add
            snip.setDate(new Date());
            snip.setCode((String)values.get("code"));
            snipList.add(0, snip); //TODO: eliminare lo show di tutti gli snipp da snipList magari con una FIFO
            meta.setSnip(snip);


            //--------- get session ad manager ------
            MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();
            logger.info("web session is: "+app_session.toString());
            EntityManager em = app_session.getEntityManager();
            //---------------------------------------------------

            Users tmp_id = em.find(Users.class ,app_session.getAttribute("user"));
            //snip.setUser(app_session.getCurrentUser());  --less network more backend
            snip.setUser(tmp_id);
            em.getTransaction().begin();
            em.setFlushMode(COMMIT);
            em.persist(snip);
            em.persist(meta);
            em.flush();
            em.getTransaction().commit();


            // Clear out the text component
            values.put("code", "");
        }
    }

    /**
     * Clears the comments.

    public void clear()
    {
        snipList.clear();
    }
    */

}
