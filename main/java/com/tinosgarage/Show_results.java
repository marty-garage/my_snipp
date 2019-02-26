package com.tinosgarage;

import javafx.util.Pair;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Show_results extends Panel {
    private static Logger logger = Logger.getAnonymousLogger();
    public Show_results(String id) {
        super(id);

        logger.info("building new Show_result");
       //this.setEnabled(false);

        this.create_repeatingView();

        //WebMarkupContainer box = new WebMarkupContainer("panel_inner_box");

        //add(box)
    }

    public Show_results(String id, Map<String, String> list){ //in items results
        super(id);
        this.create_repeatingView(list);


    }

    public void create_repeatingView(){
        this.create_repeatingView(new HashMap<>());// or
    }

    public void create_repeatingView(Map<String,String> map){
        logger.info("repeating from map: "+map.toString());
        List<Pair> tmp_map = new ArrayList<Pair>(map.keySet().stream().map(k -> new Pair<String,String>(k,map.get(k))).collect(Collectors.toList()));

        logger.info("repeating from map: "+tmp_map.toString());
       /* List<String> meta = map.values().stream().collect(Collectors.toList());
        Set<String> code_set = map.keySet(); //code is unique !?!?!?
        List<String>  list = new ArrayList<String>(code_set);*/

        logger.info(tmp_map.toString());
        ListDataProvider<Pair> provider = new ListDataProvider<Pair>(tmp_map);
        DataView<Pair> dataView = new DataView<Pair>("result_titles",provider) {

            @Override
            protected void populateItem(Item<Pair> item) {
                Pair<String,String> current = item.getModelObject();
                RepeatingView line = new RepeatingView("result_code");
                RepeatingView title = new RepeatingView("result_summary");

                title.add(new Label(title.newChildId(),current.getValue().toString().substring(0,current.getValue().toString().length()>25 ? 24 : current.getValue().length()-1)));
                logger.info("current:"+current.toString());
                line.add(new Label(line.newChildId(),current.getKey().toString()));
                item.add(title);
                item.add(line); //!!!!!!!!! the repeater belong to the item!!!!!
            }
        };
//do something with list

        addOrReplace(dataView);

    }
}
