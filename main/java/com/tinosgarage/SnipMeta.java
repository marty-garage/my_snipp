package com.tinosgarage;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.util.io.IClusterable;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity(name = "SnipMeta")
@Table(name = "SnipMeta")
public class SnipMeta implements IClusterable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private Snip snip;

    @Column(name = "description")
    private String description;


    @Column(name = "tags")
    private String tags;// = new ArrayList<String>();

    public SnipMeta(){

    }

    public SnipMeta(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return Arrays.asList(tags.split("\\s*,\\s*"));
    }

    public void setTags(List<String> tags) {
        this.tags = StringUtils.join(tags, ",");
    }

    public Snip getSnip() {
        return snip;
    }

    public void setSnip(Snip snip) {
        this.snip = snip;
    }
}
