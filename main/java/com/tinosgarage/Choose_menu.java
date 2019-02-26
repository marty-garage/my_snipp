package com.tinosgarage;

import org.apache.wicket.PageReference;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AuthorizeInstantiation("ADMIN")
public class Choose_menu extends BasePage {

    //private Form<String> form_search;
    private PageReference page;

    public Map<String,String> search(List<String> lang, String research){
        // Users tmp_id = em.find(Users.class ,app_session.getAttribute("user"));
        MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();
        logger.info("user is: "+app_session.getAttribute("user"));

        String lang_bar = lang.stream().collect(Collectors.joining("|"));
        lang_bar =",(" + lang_bar + "),";
        logger.info(lang_bar);
        EntityManager em = app_session.getEntityManager();


        List<Object[]> tmp_snip_list= em.createNativeQuery(
                "SELECT s.code, meta.description FROM Snip s INNER JOIN SnipMeta meta ON s.id=meta.id\n" +
                        "WHERE s.fk_user  = :userID AND  CONCAT(\",\", meta.tags, \",\") REGEXP :lang_bar " +
                        "AND (s.code LIKE  CONCAT('%',LOWER(:research),'%') OR meta.description LIKE  CONCAT('%',LOWER(:research),'%'))")
                .setParameter("userID", app_session.getAttribute("user"))
                .setParameter("research",research)
                .setParameter("lang_bar",lang_bar)
                .getResultList();
        em.clear();

                logger.info("from query:"+tmp_snip_list);
        return  (Map<String,String>) tmp_snip_list.stream().collect(Collectors.toMap( s-> ""+s[0],s -> ""+s[1]));//

    }

    public Choose_menu(){

        Show_results res = new Show_results("result_panel");

        page = this.getPageReference();


        //RepeatingView listItems = new RepeatingView("language_options",new Model());

        TextField search_keyword = new TextField("search_keyword",new Model<String>());



        add(res);
        //form_search.add(res);
        //form_search.autoAdd(res,this.getAssociatedMarkupStream(true));



        //--------- get session ad manager ------
        MyAuthenticatedWebSession app_session = MyAuthenticatedWebSession.getYourAppSession();

        ArrayList<String> tmp_list = (ArrayList<String>)app_session.getAttribute("tags_set");
        logger.info(tmp_list.toString());

        ListMultipleChoice listItems = new ListMultipleChoice("select_multiple",new ListModel<String>(new ArrayList<String>()),tmp_list);
        Form<String> form_search = new Form<String>("form_search"){


            @Override
            public void onSubmit(){
                List<String> languages = (List<String>)listItems.getDefaultModelObject();

                String search_string = search_keyword.getDefaultModelObjectAsString();

                logger.info("choices from model: "+languages);
                logger.info("search from model: "+search_string.toString());
                Map<String,String> map = Choose_menu.this.search(languages,search_string);
                //List<String> list = Choose_menu.this.search(null,null);
                logger.info(map.toString());
                Choose_menu.this.replace_results(map);


                //this.replaceWith(new Show_results("result_panel",list));
                logger.info("replace with executed in on Submitt");

            }
        };
        form_search.add(listItems);
            //here is missed de IModel for listItem!!!!

        form_search.add(search_keyword);
        add(form_search);
    }

    public void replace_results(Map<String,String> map){
        Show_results res = new Show_results("result_panel",map);
        this.replace(res);
    }




}


